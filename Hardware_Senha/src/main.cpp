#include <Arduino.h>
#include <Keypad.h>
#include <LiquidCrystal_I2C.h>
#include <cstddef>
#include <WiFi.h>
#include <HTTPClient.h>

#define ROW_NUM 4
#define COLUMNS_NUM 4

char keys[ROW_NUM][COLUMNS_NUM] = { //Set the key layout
    {'1', '2', '3', 'A'},
    {'4', '5', '6', 'B'},
    {'7', '8', '9', 'C'},
    {'*', '0', '#', 'D'}
};

uint8_t pin_row[ROW_NUM] = {19, 18, 5, 17}; //Set the pin numbers for the rows
uint8_t pin_col[COLUMNS_NUM] = {16, 4, 2, 15};

Keypad keypad = Keypad(makeKeymap(keys), pin_row, pin_col, ROW_NUM, COLUMNS_NUM); //Create the keypad object
LiquidCrystal_I2C lcd(0x27, 16, 2); 

const char* ssid = "Wokwi-GUEST"; //Set the WiFi SSID
const char* password = ""; //Set the WiFi password

String senha = "";
HTTPClient http;
String resposta; //Set the initial password to an empty string

const int MAX_PASSWORD_LENGTH = 8;

void showMessage(const String &line1, const String &line2 = "") {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(line1);
    lcd.setCursor(0, 1);
    lcd.print(line2);
}

void conexao() { // Function to connect to WiFi
    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid, password);
    Serial.print("Connecting to WiFi");
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
    Serial.println("\nConnected to WiFi");
}

String validarSenha(String senha) {
   
    if (senha.length() == 0) {
        return String("Senha vazia");
    }

    if (WiFi.status() != WL_CONNECTED) {
        return String("Erro de conexao");
    }

    http.begin("http://192.168.1.44:8081/API/validar");
    http.addHeader("Content-Type", "application/json");

    String payload = "{\"senha\":\"" + senha + "\"}";
    int httpCode = http.POST(payload);

    Serial.print("HTTP Code: ");
    Serial.println(httpCode);

    if (httpCode > 0) {
        if (httpCode == HTTP_CODE_OK) {
            resposta = http.getString();
        } else {
            resposta = String("Erro HTTP: ") + httpCode;
        }
    } else {
        resposta = String("Erro HTTP");
    }

    Serial.print("Resposta: ");
    Serial.println(resposta);

    http.end();
    return resposta;
}

void setup() {
    lcd.init();
    lcd.backlight();
    Serial.begin(9600);
    conexao();
    showMessage("Digite a senha:", "");
}

void loop() { // Main loop to read the keypad and display the password on the LCD
  char key = keypad.getKey();
  if (key) {
    if (key == '#') {
      showMessage("Verificando...", "");
      String resposta = validarSenha(senha);
      showMessage(resposta, "");
      delay(3000);
      senha = "";
      showMessage("Digite a senha:", "");
    } else if (key == '*') {
      senha = "";
      showMessage("Digite a senha:", "");
    } else if (senha.length() < MAX_PASSWORD_LENGTH) {
      senha += key;
      lcd.setCursor(0, 1);
      lcd.print(senha);
    }
  }
}