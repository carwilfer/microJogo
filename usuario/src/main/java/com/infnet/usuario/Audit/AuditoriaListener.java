package com.infnet.usuario.Audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditoriaListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisaoEntidade auditRevisaoEntidade = (AuditRevisaoEntidade) revisionEntity;
        auditRevisaoEntidade.setModifiedBy("system");
    }
}
