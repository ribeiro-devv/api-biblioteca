package com.mr.demo.scurity.demo.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UsuarioOutput {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "John Smith")
    private String nome;
    @Schema(example = "john@biblioteca.com")
    private String email;
    @Schema(example = "2025-01-01T00:00:00-03:00")
    private OffsetDateTime dataCadastro;
}
