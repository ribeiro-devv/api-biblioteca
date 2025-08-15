package com.mr.demo.scurity.demo.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroOutput {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "As Aventuras de Peter Pan")
    private String titulo;
    private UsuarioOutput usuario;
}
