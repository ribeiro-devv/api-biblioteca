package com.mr.demo.scurity.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "As Aventuras de Peter Pan", required = true)
    @NotBlank
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Biblioteca biblioteca;

    @OneToMany(mappedBy = "livro")
    @JsonIgnore
    private Set<PedidoLivro> pedidos = new HashSet<>();
}
