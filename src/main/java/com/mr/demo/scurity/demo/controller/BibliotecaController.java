package com.mr.demo.scurity.demo.controller;

import com.mr.demo.scurity.demo.dto.output.BibliotecaOutput;
import com.mr.demo.scurity.demo.model.Biblioteca;
import com.mr.demo.scurity.demo.security.CheckSecurity;
import com.mr.demo.scurity.demo.service.BibliotecaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bibliotecas", produces = MediaType.APPLICATION_JSON_VALUE)
public class BibliotecaController {

    private final BibliotecaService service;

    public BibliotecaController(BibliotecaService service) {
        this.service = service;
    }

    @CheckSecurity.Biblioteca.PodeConsultar
    @GetMapping
    public List<BibliotecaOutput> listar() {
        return service.listar();
    }

    @CheckSecurity.Biblioteca.PodeConsultar
    @GetMapping("/{bibliotecaId}")
    public Biblioteca buscar(@PathVariable Long bibliotecaId) {
        return service.buscarOuFalhar(bibliotecaId);
    }

    @CheckSecurity.Biblioteca.PodeGerenciarCadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BibliotecaOutput adicionar(@RequestBody Biblioteca biblioteca) {
        return service.salvar(biblioteca);
    }

    @CheckSecurity.Biblioteca.PodeGerenciarCadastro
    @PutMapping("/{livroId}")
    public BibliotecaOutput atualizar(@PathVariable Long bibliotecaId, @RequestBody Biblioteca biblioteca) {
        Biblioteca bibliotecaAtual = service.buscarOuFalhar(bibliotecaId);
        bibliotecaAtual.setNome(biblioteca.getNome());
        return service.salvar(bibliotecaAtual);
    }

    @CheckSecurity.Biblioteca.PodeGerenciarCadastro
    @DeleteMapping("/{livroId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long bibliotecaId) {
        service.excluir(bibliotecaId);
    }

    @CheckSecurity.Biblioteca.PodeGerenciarCadastro
    @PutMapping("/{bibliotecaId}/associar/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long bibliotecaId, @PathVariable Long usuarioId){
        service.associarResponsavel(bibliotecaId, usuarioId);
    }

    @CheckSecurity.Biblioteca.PodeGerenciarCadastro
    @PutMapping("/{bibliotecaId}/desassociar/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long bibliotecaId, @PathVariable Long usuarioId){
        service.desassociarResponsavel(bibliotecaId, usuarioId);
    }
}
