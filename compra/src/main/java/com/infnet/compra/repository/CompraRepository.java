package com.infnet.compra.repository;

import com.infnet.compra.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query("from Compra bi where bi.usuarioId=:id")
    List<Compra> encontrarPorUsuarioId(Long id);
}
