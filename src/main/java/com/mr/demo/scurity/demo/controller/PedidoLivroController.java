package com.mr.demo.scurity.demo.controller;

import com.mr.demo.scurity.demo.dto.output.PedidoLivroOutput;
import com.mr.demo.scurity.demo.model.PedidoLivro;
import com.mr.demo.scurity.demo.security.CheckSecurity;
import com.mr.demo.scurity.demo.service.PedidoLivroService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bibliotecas/{bibliotecaId}/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoLivroController {

    private final PedidoLivroService pedidoLivroService;

    public PedidoLivroController(PedidoLivroService pedidoLivroService) {
        this.pedidoLivroService = pedidoLivroService;
    }

    @CheckSecurity.PedidosLivro.PodeBuscar
    @GetMapping("/{pedidoLivroId}")
    public PedidoLivroOutput buscar(@PathVariable Long bibliotecaId, @PathVariable Long pedidoLivroId) {
        return pedidoLivroService.buscar(bibliotecaId, pedidoLivroId);
    }

    @CheckSecurity.PedidosLivro.PodeCriarPedido
    @PostMapping
    public PedidoLivroOutput adicionar(@PathVariable Long bibliotecaId, @RequestBody PedidoLivro pedidoLivro) {
        return pedidoLivroService.adicionar(bibliotecaId, pedidoLivro);
    }
}
