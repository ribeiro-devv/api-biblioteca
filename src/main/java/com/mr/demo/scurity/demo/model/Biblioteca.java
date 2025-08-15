package com.mr.demo.scurity.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "biblioteca")
    @JsonIgnore
    private List<Livro> livro = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "biblioteca_usuario_responsavel",
            joinColumns = @JoinColumn(name = "biblioteca_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    public boolean adicionarResponsavel(Usuario usuario) {
        return getResponsaveis().add(usuario);
    }

    public boolean removerResponsavel(Usuario usuario) {
        return getResponsaveis().remove(usuario);
    }
}
