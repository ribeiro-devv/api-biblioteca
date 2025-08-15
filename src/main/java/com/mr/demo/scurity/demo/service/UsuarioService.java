package com.mr.demo.scurity.demo.service;

import com.mr.demo.scurity.demo.model.Usuario;
import com.mr.demo.scurity.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario buscarOuFalhar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
    }
}
