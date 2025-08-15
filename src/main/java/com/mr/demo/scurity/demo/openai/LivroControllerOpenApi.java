package com.mr.demo.scurity.demo.openai;

import com.mr.demo.scurity.demo.dto.output.LivroOutput;
import com.mr.demo.scurity.demo.model.Livro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Livros")
public interface LivroControllerOpenApi {

    @Operation(summary = "Lista os Livros", description = "Lista os livros cadastrados em uma determinada biblioteca")
    List<LivroOutput> listar(@Parameter(description = "Id de uma biblioteca", example = "1", required = true)
                             Long bibliotecaId);

    @Operation(summary = "Busca um Livro por ID", description = "Busca um livro em uma determinada biblioteca", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do livro inválido",
                    content = @Content(schema = @Schema(ref = "Problema"))
            ),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                    content = @Content(schema = @Schema(ref = "Problema"))
            )
    })
    Livro buscar(@Parameter(description = "Id de uma biblioteca", example = "1", required = true)
                 Long bibliotecaId,
                 @Parameter(description = "Id de um livro", example = "1", required = true)
                 Long livroId);

    @Operation(summary = "Cadastra um Livro", description = "Cadastra um livro em uma determinada biblioteca")
    Livro adicionar(@Parameter(description = "Id de uma biblioteca", example = "1", required = true)
                    Long bibliotecaId,
                    @RequestBody(description = "Representação de um novo livro")
                    Livro livro);

    @Operation(summary = "Atualiza um Livro por ID", description = "Atualiza um livro em uma determinada biblioteca")
    Livro atualizar(@Parameter(description = "Id de uma biblioteca", example = "1", required = true)
                    Long bibliotecaId,
                    @Parameter(description = "Id de um livro", example = "1", required = true)
                    Long livroId,
                    @RequestBody(description = "Representação de um livro para atualização")
                    Livro livro);

    @Operation(summary = "Remove um Livro por ID", description = "Remove um livro em uma determinada biblioteca\s")
    void remover(@Parameter(description = "Id de uma biblioteca", example = "1", required = true)
                 Long bibliotecaId,
                 @Parameter(description = "Id de um livro", example = "1", required = true)
                 Long livroId);
}
