package com.mr.demo.scurity.demo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dataCadastro;

    @ManyToMany
    @JoinTable(
            name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private Set<Grupo> grupos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    private Set<PedidoLivro> pedidos = new HashSet<>();
}