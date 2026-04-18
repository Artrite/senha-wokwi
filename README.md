# 🔐 Sistema de Senha com ESP32 + Spring Boot

Este projeto consiste em um sistema de validação de senha utilizando um ESP32 (simulado no Wokwi) integrado a uma API desenvolvida em Spring Boot. O usuário digita a senha em um keypad, e o sistema valida essa senha através de uma requisição HTTP para o backend.

---

## 🧠 Tecnologias utilizadas

* ESP32 (Wokwi)
* PlatformIO
* C++
* Spring Boot
* Java
* Banco de dados SQL (MySQL ou similar)
* HTTP (API REST)

---

## ⚙️ Como rodar o projeto

### 📌 Pré-requisitos

Antes de começar, você precisa ter instalado:

* Java (JDK 17+ recomendado)
* Maven ou Gradle
* PlatformIO (VS Code ou IntelliJ)
* Banco de dados SQL (MySQL)
* Git

---

## 🗄️ Banco de Dados

Crie um banco de dados e execute o seguinte script SQL:

```sql
CREATE DATABASE senha_db;

USE senha_db;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

-- Inserindo uma senha de teste
INSERT INTO usuarios (senha) VALUES ('1234');
```

---

## 🔧 Configuração do Spring Boot

No arquivo `application.properties`, configure:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/senha_db
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
server.port=8081
server.address=0.0.0.0
```

⚠️ **Importante:**

* Substitua `SEU_USUARIO` e `SUA_SENHA` pelos dados do seu banco
* A porta deve bater com a usada no ESP (`8081` no seu caso)

---

## 🌐 Configuração de IP

Você precisa usar o IP da sua máquina local.

### Como descobrir:

No terminal:

```bash
ipconfig
```

Procure algo como:

```
IPv4 Address: 192.168.x.x
```

---

## 📡 Ajustar no ESP (main.cpp)

No seu código, altere a URL da API:

```cpp
http.begin("http://SEU_IP:8081/API/validar");
```

Exemplo:

```cpp
http.begin("http://192.168.2.64:8081/API/validar");
```

---

## 🚀 Executando o projeto

### 🔹 Backend (Spring Boot)

1. Abra o projeto
2. Rode a aplicação:

```bash
./mvnw spring-boot:run
```

ou execute a classe principal (`SenhaApplication`)

---

### 🔹 ESP32 (PlatformIO + Wokwi)

1. Compile:

```bash
pio run
```

3. Inicie a simulação no Wokwi pelo arquivo diagram.json

---
### 🔹 Criar um usuário com senha

Via SQL: 

```
INSERT INTO usuarios (senha) VALUES ('1234');
```

Via API (Postman) 

Método: POST
```
URL:
http://SEU_IP:8081/API/cadastrar
```

```
Body (JSON):
{
  "nome":"João",
  "senha": "1234"
}
```
## 📲 Como funciona

1. Usuário digita a senha no keypad
2. Pressiona `#` para enviar
3. ESP32 envia um JSON para a API:

```json
{
  "nome":"João",
  "senha": "1234"
}
```

4. API valida no banco de dados
5. Retorna resposta
6. Resultado é exibido no LCD

---

## ⚠️ Problemas comuns

### ❌ Erro de conexão

* Verifique se o WiFi está conectado
* Confirme o IP correto
* Veja se o backend está rodando

---

### ❌ Connection reset by peer

* API não está acessível
* IP incorreto
* Porta errada

---

