package com.mr.demo.scurity.demo.repository;

import com.mr.demo.scurity.demo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findAllByBibliotecaId(Long bibliotecaId);

    @Query("from Livro where biblioteca.id = :biblioteca and id = :livro")
    Optional<Livro> findByBibliotecaIdAndLivroId(@Param("biblioteca") Long bibliotecaId, @Param("livro") Long livroId);
}
