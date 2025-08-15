package com.mr.demo.scurity.demo.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;
}
