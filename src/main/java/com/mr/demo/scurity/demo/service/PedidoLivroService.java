package com.mr.demo.scurity.demo.service;

import com.mr.demo.scurity.demo.dto.output.LivroOutputSemUsuario;
import com.mr.demo.scurity.demo.dto.output.PedidoLivroOutput;
import com.mr.demo.scurity.demo.dto.output.UsuarioOutput;
import com.mr.demo.scurity.demo.model.*;
import com.mr.demo.scurity.demo.repository.PedidoLivroRepository;
import com.mr.demo.scurity.demo.security.BibliotecaSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Service
public class PedidoLivroService {

    private final PedidoLivroRepository pedidoLivroRepository;
    private final BibliotecaService bibliotecaService;
    private final BibliotecaSecurity bibliotecaSecurity;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public PedidoLivroService(PedidoLivroRepository pedidoLivroRepository, BibliotecaService bibliotecaService,
                              BibliotecaSecurity bibliotecaSecurity, UsuarioService usuarioService, LivroService livroService) {
        this.pedidoLivroRepository = pedidoLivroRepository;
        this.bibliotecaService = bibliotecaService;
        this.bibliotecaSecurity = bibliotecaSecurity;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    public PedidoLivroOutput adicionar(Long bibliotecaId, PedidoLivro pedidoLivro) {
        Biblioteca biblioteca = bibliotecaService.buscarOuFalhar(bibliotecaId);
        Usuario usuario = usuarioService.buscarOuFalhar(bibliotecaSecurity.getUsuarioId());
        Livro livro = livroService.buscarOuFalhar(biblioteca.getId(), pedidoLivro.getLivro().getId());

        pedidoLivro.setDataPedido(LocalDate.now());
        pedidoLivro.setDataPrevistaEntrega(LocalDate.now().plusMonths(1));
        pedidoLivro.setLivro(livro);
        pedidoLivro.setUsuario(usuario);
        pedidoLivro.setStatusPedido(StatusPedido.PENDENTE);
        PedidoLivro pedidoLivroSalvo = pedidoLivroRepository.save(pedidoLivro);

        PedidoLivroOutput pedidoLivroOutput = new PedidoLivroOutput();
        pedidoLivroOutput.setId(pedidoLivroSalvo.getId());
        pedidoLivroOutput.setDataPedido(pedidoLivroSalvo.getDataPedido());
        pedidoLivroOutput.setStatusPedido(pedidoLivroSalvo.getStatusPedido());
        pedidoLivroOutput.setDataPrevistaEntrega(pedidoLivroSalvo.getDataPrevistaEntrega());

        UsuarioOutput usuarioOutput = new UsuarioOutput();
        usuarioOutput.setId(pedidoLivroSalvo.getUsuario().getId());
        usuarioOutput.setNome(pedidoLivroSalvo.getUsuario().getNome());
        usuarioOutput.setEmail(pedidoLivroSalvo.getUsuario().getEmail());
        usuarioOutput.setDataCadastro(pedidoLivroSalvo.getUsuario().getDataCadastro());

        pedidoLivroOutput.setUsuario(usuarioOutput);

        LivroOutputSemUsuario livroOutputSemUsuario = new LivroOutputSemUsuario();
        livroOutputSemUsuario.setId(pedidoLivroSalvo.getLivro().getId());
        livroOutputSemUsuario.setTitulo(pedidoLivroSalvo.getLivro().getTitulo());
        livroOutputSemUsuario.setBibliotecaId(pedidoLivroSalvo.getLivro().getBiblioteca().getId());
        livroOutputSemUsuario.setBibliotecaNome(pedidoLivroSalvo.getLivro().getBiblioteca().getNome());

        pedidoLivroOutput.setLivro(livroOutputSemUsuario);

        return pedidoLivroOutput;
    }

    public PedidoLivroOutput buscar(Long bibliotecaId, @PathVariable Long pedidoLivroId) {
        PedidoLivro pedidoLivro = buscarOuFalhar(bibliotecaId, pedidoLivroId);

        PedidoLivroOutput pedidoLivroOutput = new PedidoLivroOutput();
        pedidoLivroOutput.setId(pedidoLivro.getId());
        pedidoLivroOutput.setDataPedido(pedidoLivro.getDataPedido());
        pedidoLivroOutput.setStatusPedido(pedidoLivro.getStatusPedido());
        pedidoLivroOutput.setDataPrevistaEntrega(pedidoLivro.getDataPrevistaEntrega());

        UsuarioOutput usuarioOutput = new UsuarioOutput();
        usuarioOutput.setId(pedidoLivro.getUsuario().getId());
        usuarioOutput.setNome(pedidoLivro.getUsuario().getNome());
        usuarioOutput.setEmail(pedidoLivro.getUsuario().getEmail());
        usuarioOutput.setDataCadastro(pedidoLivro.getUsuario().getDataCadastro());

        pedidoLivroOutput.setUsuario(usuarioOutput);

        LivroOutputSemUsuario livroOutputSemUsuario = new LivroOutputSemUsuario();
        livroOutputSemUsuario.setId(pedidoLivro.getLivro().getId());
        livroOutputSemUsuario.setTitulo(pedidoLivro.getLivro().getTitulo());
        livroOutputSemUsuario.setBibliotecaId(pedidoLivro.getLivro().getBiblioteca().getId());
        livroOutputSemUsuario.setBibliotecaNome(pedidoLivro.getLivro().getBiblioteca().getNome());

        pedidoLivroOutput.setLivro(livroOutputSemUsuario);

        return pedidoLivroOutput;
    }

    public PedidoLivro buscarOuFalhar(Long bibliotecaId, Long pedidoId) {
        return pedidoLivroRepository.findByBibliotecaIdAndPedidoLivroId(bibliotecaId, pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + pedidoId));
    }
}
