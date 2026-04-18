package senha.Controller;

import senha.DTO.UsuarioDTO;
import senha.Entity.Usuario;
import senha.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.Optional;

@RestController
@RequestMapping(value = "/API")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioServices;

    @GetMapping
    public List<Usuario> findAll() {
        return usuarioServices.findAll();
    }

    @GetMapping (value = "/{id}")
    public Usuario findById(@PathVariable Long id) {
        return usuarioServices.findbyID(id);
    }

    @PostMapping("/cadastrar") 
    public ResponseEntity<Usuario> saveUsuario (@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServices.criarUsuario(usuario));
    }

    @PostMapping("/validar") //Function that receives the payload with the password and checks if it exists in the database
    public ResponseEntity<String> validarSenha (@RequestBody Map<String, String> payload) {
       String senha = payload.get("senha");
        boolean exists = usuarioServices.existsBySenha(senha);
        return ResponseEntity.ok(exists ? "Acesso Liberado" : "Acesso Negado");
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity<Optional<Usuario>> updateUsuario (@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        Optional<Usuario> mudanca = usuarioServices.updateUsuario(id, usuario);
        if (mudanca.isEmpty()) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário Inexistente");
        }
        return ResponseEntity.status(HttpStatus.OK).body(mudanca);
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity<Optional<Usuario>> deleteUsuario (@PathVariable Long id) {
        Optional <Usuario> usuario = usuarioServices.deleteUsuario(id);

        if (usuario.isEmpty()) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usúario Inexistente");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }
}