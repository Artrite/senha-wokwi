package senha.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuarios")
    private Long id;

    private String nome;

    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long idusuarios) {
        this.id = idusuarios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}


