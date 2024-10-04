package com.infnet.avaliacao.repository;

import com.infnet.avaliacao.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    @Query("from Avaliacao r where r.usuarioId = :id")
    List<Avaliacao> encontrarAvaliacaoId(Long id);
}
