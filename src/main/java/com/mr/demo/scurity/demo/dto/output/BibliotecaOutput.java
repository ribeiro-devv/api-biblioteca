package com.mr.demo.scurity.demo.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class BibliotecaOutput {

    private Long id;
    private String nome;
    private List<LivroOutput> livros;
    private Set<UsuarioOutput> responsaveis;
}
