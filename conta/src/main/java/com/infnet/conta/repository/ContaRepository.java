package com.infnet.conta.repository;

import com.infnet.conta.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("from Conta bi where bi.usuarioId=:id")
    Conta encontrarPorUsuarioId(Long id);
}
