package com.mr.demo.scurity.demo.service;

import com.mr.demo.scurity.demo.dto.output.LivroOutput;
import com.mr.demo.scurity.demo.dto.output.UsuarioOutput;
import com.mr.demo.scurity.demo.exception.LivroNaoEncontradoException;
import com.mr.demo.scurity.demo.model.Biblioteca;
import com.mr.demo.scurity.demo.model.Livro;
import com.mr.demo.scurity.demo.model.Usuario;
import com.mr.demo.scurity.demo.repository.LivroRepository;
import com.mr.demo.scurity.demo.security.BibliotecaSecurity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final BibliotecaSecurity bibliotecaSecurity;
    private final BibliotecaService bibliotecaService;
    private final UsuarioService usuarioService;

    public LivroService(LivroRepository livroRepository, BibliotecaSecurity bibliotecaSecurity, BibliotecaService bibliotecaService,
                        UsuarioService usuarioService) {
        this.livroRepository = livroRepository;
        this.bibliotecaSecurity = bibliotecaSecurity;
        this.bibliotecaService = bibliotecaService;
        this.usuarioService = usuarioService;
    }

    public List<LivroOutput> listar(Long bibliotecaId) {
        List<Livro> livros = livroRepository.findAllByBibliotecaId(bibliotecaId); 
        List<LivroOutput> livrosOutputs = new ArrayList<>();

        for (Livro livro : livros) {
            LivroOutput livroOutput = new LivroOutput();
            livroOutput.setId(livro.getId());
            livroOutput.setTitulo(livro.getTitulo());

            UsuarioOutput usuarioOutput = new UsuarioOutput();
            usuarioOutput.setId(livro.getUsuario().getId());
            usuarioOutput.setNome(livro.getUsuario().getNome());
            usuarioOutput.setEmail(livro.getUsuario().getEmail());
            usuarioOutput.setDataCadastro(livro.getUsuario().getDataCadastro());

            livroOutput.setUsuario(usuarioOutput);

            livrosOutputs.add(livroOutput);
        }
        return livrosOutputs;
    }

    public Livro salvar(Long bibliotecaId, Livro livro) {
        Biblioteca biblioteca = bibliotecaService.buscarOuFalhar(bibliotecaId);

        Usuario usuario = usuarioService.buscarOuFalhar(bibliotecaSecurity.getUsuarioId());

        livro.setUsuario(usuario);
        livro.setBiblioteca(biblioteca);

        return livroRepository.save(livro);
    }

    public void excluir(Long bibliotecaId, Long livroId) {
        Livro livro = buscarOuFalhar(bibliotecaId, livroId);
        livroRepository.deleteById(livro.getId());
    }

    public Livro buscarOuFalhar(Long bibliotecaId, Long livroId) {
        return livroRepository.findByBibliotecaIdAndLivroId(bibliotecaId, livroId)
                .orElseThrow(() -> new LivroNaoEncontradoException(livroId));
    }
}
