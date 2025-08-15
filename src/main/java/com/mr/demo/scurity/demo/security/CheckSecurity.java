package com.mr.demo.scurity.demo.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

//    public @interface Livros {
//
//        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
//        @Retention(RetentionPolicy.RUNTIME)
//        @Target(ElementType.METHOD)
//        public @interface PodeConsultar {}
//
//        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_LIVROS')")
//        @Retention(RetentionPolicy.RUNTIME)
//        @Target(ElementType.METHOD)
//        public @interface PodeGerenciarCadastro {}
//    }

    public @interface Biblioteca {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_LIVROS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeGerenciarCadastro {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('EDITAR_LIVROS') or " +
                "@bibliotecaSecurity.gerenciaBiblioteca(#bibliotecaId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeGerenciarFuncionamento {}
    }

    public @interface PedidosLivro {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS_LIVRO') or " +
                "@bibliotecaSecurity.usuarioAutenticadoIgual(returnObject.usuario.id) or " +
                "@bibliotecaSecurity.gerenciaBiblioteca(returnObject.livro.bibliotecaId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeBuscar {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeCriarPedido {}
    }
}
