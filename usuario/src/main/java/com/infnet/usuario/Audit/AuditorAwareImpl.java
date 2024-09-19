package com.infnet.usuario.Audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Aqui você pode retornar o usuário autenticado, ou uma string fixa como "system"
        return Optional.of("system");
    }
}
