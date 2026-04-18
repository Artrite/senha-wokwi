package senha.Repository;

import senha.Entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsBySenha(String senha); // Function to check if a password exists in the database
}
