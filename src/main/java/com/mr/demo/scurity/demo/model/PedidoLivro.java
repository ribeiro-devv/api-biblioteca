package com.mr.demo.scurity.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class PedidoLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    private LocalDate dataPedido;
    private LocalDate dataPrevistaEntrega;
    private LocalDate dataEntregaEfetiva;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Livro livro;
}
