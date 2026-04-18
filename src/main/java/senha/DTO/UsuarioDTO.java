package senha.DTO;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO (@NotBlank String nome, @NotBlank String senha) {

}
