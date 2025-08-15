package com.mr.demo.scurity.demo.service;

import com.mr.demo.scurity.demo.dto.output.BibliotecaOutput;
import com.mr.demo.scurity.demo.dto.output.LivroOutput;
import com.mr.demo.scurity.demo.dto.output.UsuarioOutput;
import com.mr.demo.scurity.demo.model.Biblioteca;
import com.mr.demo.scurity.demo.model.Livro;
import com.mr.demo.scurity.demo.model.Usuario;
import com.mr.demo.scurity.demo.repository.BibliotecaRepository;
import com.mr.demo.scurity.demo.repository.UsuarioRepository;
import com.mr.demo.scurity.demo.security.BibliotecaSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;
    private final BibliotecaSecurity bibliotecaSecurity;
    private final UsuarioService usuarioService;

    public BibliotecaService(BibliotecaRepository bibliotecaRepository, UsuarioRepository usuarioRepository,
                             BibliotecaSecurity bibliotecaSecurity, UsuarioService usuarioService) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.usuarioRepository = usuarioRepository;
        this.bibliotecaSecurity = bibliotecaSecurity;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public List<BibliotecaOutput> listar() {
        List<Biblioteca> bibliotecas = bibliotecaRepository.findAll();
        List<BibliotecaOutput> bibliotecaOutputs = new ArrayList<>();


        for (Biblioteca biblioteca : bibliotecas) {
            BibliotecaOutput bibliotecaOutput = new BibliotecaOutput();
            bibliotecaOutput.setId(biblioteca.getId());
            bibliotecaOutput.setNome(biblioteca.getNome());

            List<LivroOutput> livros = new ArrayList<>();
            for (Livro livro : biblioteca.getLivro()) {
                LivroOutput livroOutput = new LivroOutput();
                livroOutput.setId(livro.getId());
                livroOutput.setTitulo(livro.getTitulo());

                Usuario usuario = livro.getUsuario();
                UsuarioOutput usuarioOutput = new UsuarioOutput();
                usuarioOutput.setId(usuario.getId());
                usuarioOutput.setNome(usuario.getNome());
                usuarioOutput.setEmail(usuario.getEmail());
                usuarioOutput.setDataCadastro(usuario.getDataCadastro());

                livroOutput.setUsuario(usuarioOutput);
                livros.add(livroOutput);
            }
            bibliotecaOutput.setLivros(livros);

            Set<UsuarioOutput> responsaveis = new HashSet<>();
            for (Usuario usuario : biblioteca.getResponsaveis()) {
                UsuarioOutput usuarioOutput = new UsuarioOutput();
                usuarioOutput.setId(usuario.getId());
                usuarioOutput.setNome(usuario.getNome());
                usuarioOutput.setEmail(usuario.getEmail());
                usuarioOutput.setDataCadastro(usuario.getDataCadastro());
                responsaveis.add(usuarioOutput);
            }
            bibliotecaOutput.setResponsaveis(responsaveis);

            bibliotecaOutputs.add(bibliotecaOutput);
        }

        return bibliotecaOutputs;
    }

    @Transactional
    public BibliotecaOutput salvar(Biblioteca biblioteca) {
        biblioteca.setNome(biblioteca.getNome());
        Biblioteca bibliotecaSalva = bibliotecaRepository.save(biblioteca);

        BibliotecaOutput bibliotecaOutput = new BibliotecaOutput();
        bibliotecaOutput.setId(bibliotecaSalva.getId());
        bibliotecaOutput.setNome(bibliotecaSalva.getNome());

        List<LivroOutput> livros = new ArrayList<>();
        if (bibliotecaSalva.getLivro() != null) {
            for (Livro livro : bibliotecaSalva.getLivro()) {
                LivroOutput livroOutput = new LivroOutput();
                livroOutput.setId(livro.getId());
                livroOutput.setTitulo(livro.getTitulo());

                Usuario usuario = livro.getUsuario();
                UsuarioOutput usuarioOutput = new UsuarioOutput();
                usuarioOutput.setId(usuario.getId());
                usuarioOutput.setNome(usuario.getNome());
                usuarioOutput.setEmail(usuario.getEmail());
                usuarioOutput.setDataCadastro(usuario.getDataCadastro());

                livroOutput.setUsuario(usuarioOutput);
                livros.add(livroOutput);
            }
        }
        bibliotecaOutput.setLivros(livros);

        Set<UsuarioOutput> responsaveis = new HashSet<>();
        if (bibliotecaSalva.getResponsaveis() != null) {
            for (Usuario usuario : bibliotecaSalva.getResponsaveis()) {
                UsuarioOutput usuarioOutput = new UsuarioOutput();
                usuarioOutput.setId(usuario.getId());
                usuarioOutput.setNome(usuario.getNome());
                usuarioOutput.setEmail(usuario.getEmail());
                usuarioOutput.setDataCadastro(usuario.getDataCadastro());
                responsaveis.add(usuarioOutput);
            }
        }
        bibliotecaOutput.setResponsaveis(responsaveis);


        return bibliotecaOutput;
    }

    @Transactional
    public void associarResponsavel(Long bibliotecaId, Long usuarioId) {
        Biblioteca biblioteca = buscarOuFalhar(bibliotecaId);
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        biblioteca.adicionarResponsavel(usuario);
    }

    @Transactional
    public void desassociarResponsavel(Long bibliotecaId, Long usuarioId) {
        Biblioteca biblioteca = buscarOuFalhar(bibliotecaId);
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        biblioteca.removerResponsavel(usuario);
    }

    public Biblioteca buscarOuFalhar(Long id) {
        return bibliotecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado: " + id));
    }

    public void excluir(Long id) {
        bibliotecaRepository.deleteById(id);
    }

}
