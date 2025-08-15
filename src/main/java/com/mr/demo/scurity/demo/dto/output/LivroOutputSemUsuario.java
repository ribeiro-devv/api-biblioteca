package com.mr.demo.scurity.demo.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroOutputSemUsuario {

    private Long id;
    private String titulo;
    private Long bibliotecaId;
    private String bibliotecaNome;
}
