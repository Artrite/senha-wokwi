package senha.Service;

import senha.Entity.Usuario;
import senha.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findbyID(Long id) {
        Usuario findbyID = usuarioRepository.findById(id).orElse(null);
        return findbyID;
    }

    public boolean existsBySenha(String senha) { // Function to check if a password exists in the database
        return usuarioRepository.existsBySenha(senha);
    }

    public Optional<Usuario> updateUsuario(Long id, Usuario newusuario) {
        Optional<Usuario> findbyID = usuarioRepository.findById(id);
        if (findbyID.isEmpty()) {
            return findbyID;
        }
        newusuario.setId(id);
        return Optional.of(usuarioRepository.save(newusuario));
    }

    public Optional<Usuario> deleteUsuario (Long id) {
        Optional<Usuario> findbyID = usuarioRepository.findById(id);
        if (findbyID.isPresent()) {
            usuarioRepository.deleteById(id);
        }
        return findbyID;
    }
}
