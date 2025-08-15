package com.mr.demo.scurity.demo.repository;

import com.mr.demo.scurity.demo.model.Livro;
import com.mr.demo.scurity.demo.model.PedidoLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoLivroRepository extends JpaRepository<PedidoLivro, Long> {

    @Query("SELECT pl FROM PedidoLivro pl WHERE pl.livro.biblioteca.id = :bibliotecaId AND pl.id = :pedidoLivroId")
    Optional<PedidoLivro> findByBibliotecaIdAndPedidoLivroId(@Param("bibliotecaId") Long bibliotecaId,
                                                             @Param("pedidoLivroId") Long pedidoLivroId);

}
