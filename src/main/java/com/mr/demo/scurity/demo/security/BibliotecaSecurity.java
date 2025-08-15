package com.mr.demo.scurity.demo.security;

import com.mr.demo.scurity.demo.repository.BibliotecaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class BibliotecaSecurity {

    public final BibliotecaRepository bibliotecaRepository;

    public BibliotecaSecurity(BibliotecaRepository bibliotecaRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        Object usuarioId = jwt.getClaim("usuario_id");

        if (usuarioId == null) {
            return null;
        }

        return Long.valueOf(usuarioId.toString());

    }

    public boolean gerenciaBiblioteca(Long bibliotecaId) {
        return bibliotecaRepository.existeResponsavel(bibliotecaId, getUsuarioId());
    }

    public boolean usuarioAutenticadoIgual(Long usuarioId) {
        return getUsuarioId() != null && usuarioId != null && getUsuarioId().equals(usuarioId);
    }
}
