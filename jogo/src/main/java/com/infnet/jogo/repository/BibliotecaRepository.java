package com.infnet.jogo.repository;

import com.infnet.jogo.model.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
    @Query("from Biblioteca bi where bi.jogadorId=:id")
    Optional<Biblioteca> findByJogadorId(Long id);
}
