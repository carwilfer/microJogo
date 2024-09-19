package com.infnet.usuario.Audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.time.LocalDateTime;

@Entity
@RevisionEntity(AuditoriaListener.class)
@Table(name = "audit_revision")
@Getter
@Setter
@NoArgsConstructor
public class AuditRevisaoEntidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private int id;

    private String modifiedBy;

    @RevisionTimestamp
    private LocalDateTime modifiedDate;
}
