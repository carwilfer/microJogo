package com.infnet.usuario.repository;

import com.infnet.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    //Optional<Usuario> findByEmail(String email);
    //Optional<Usuario> findByCpf(String cpf);
    //Optional<Usuario> findByCnpj(String cnpj);
}
