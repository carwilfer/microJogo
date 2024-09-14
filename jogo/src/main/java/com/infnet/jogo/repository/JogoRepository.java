package com.infnet.jogo.repository;

import com.infnet.jogo.dto.JogoDTO;
import com.infnet.jogo.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
    @Query("from Jogo r where r.empresaId = :id")
    List<Jogo> encontrarEmpresasId(Long id);
}
