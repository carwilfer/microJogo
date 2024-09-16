package com.infnet.jogador.repository;

import com.infnet.jogador.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    Optional<Jogador> findByEmail(String email);
    Optional<Jogador> findByCpf(String cpf);
    Optional<Jogador> findByUsuarioId(Long usuarioId);
}
