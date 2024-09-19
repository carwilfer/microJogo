package com.infnet.usuario.Audit;

import com.infnet.usuario.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioAuditService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Usuario> getRevisoes(Long usuarioId) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<Number> revisoes = auditReader.getRevisions(Usuario.class, usuarioId);

        List<Usuario> historico = new ArrayList<>();
        for (Number revisao : revisoes) {
            Usuario revisaoUsuario = auditReader.find(Usuario.class, usuarioId, revisao);
            historico.add(revisaoUsuario);
        }
        return historico;
    }
}
