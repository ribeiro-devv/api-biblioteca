package com.mr.demo.scurity.demo.controller;

import com.mr.demo.scurity.demo.dto.output.LivroOutput;
import com.mr.demo.scurity.demo.model.Livro;
import com.mr.demo.scurity.demo.openai.LivroControllerOpenApi;
import com.mr.demo.scurity.demo.security.CheckSecurity;
import com.mr.demo.scurity.demo.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/bibliotecas/{bibliotecaId}/livros", produces = MediaType.APPLICATION_JSON_VALUE)
public class LivroController implements LivroControllerOpenApi {

    @Autowired
    private LivroService service;

    @CheckSecurity.Biblioteca.PodeConsultar
    @GetMapping
    public List<LivroOutput> listar(@PathVariable Long bibliotecaId) {
        return service.listar(bibliotecaId);
    }

    @CheckSecurity.Biblioteca.PodeConsultar
    @GetMapping("/{livroId}")
    public Livro buscar(@PathVariable Long bibliotecaId, @PathVariable Long livroId) {
        return service.buscarOuFalhar(bibliotecaId, livroId);
    }

    @CheckSecurity.Biblioteca.PodeGerenciarFuncionamento
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro adicionar(@PathVariable Long bibliotecaId,@Valid @RequestBody Livro livro) {
        return service.salvar(bibliotecaId, livro);
    }

    @CheckSecurity.Biblioteca.PodeGerenciarFuncionamento
    @PutMapping("/{livroId}")
    public Livro atualizar(@PathVariable Long bibliotecaId, @PathVariable Long livroId, @RequestBody Livro livro) {
        Livro livroAtual = service.buscarOuFalhar(bibliotecaId, livroId);
        livroAtual.setTitulo(livro.getTitulo());
        return service.salvar(bibliotecaId, livroAtual);
    }

    @CheckSecurity.Biblioteca.PodeGerenciarFuncionamento
    @DeleteMapping("/{livroId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long bibliotecaId, @PathVariable Long livroId) {
        service.excluir(bibliotecaId, livroId);
    }
}
