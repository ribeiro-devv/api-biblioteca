package com.mr.demo.scurity.demo.repository;

import com.mr.demo.scurity.demo.model.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Biblioteca b JOIN b.responsaveis r " +
            "WHERE b.id = :biblioteca AND r.id = :usuario")
    boolean existeResponsavel(@Param("biblioteca") Long bibliotecaId, @Param("usuario") Long usuarioId);
}
