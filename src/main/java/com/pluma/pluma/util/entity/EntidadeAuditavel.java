package com.pluma.pluma.util.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class EntidadeAuditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "habilitado", nullable = false)
    private boolean habilitado = true;

    @CreatedBy
    @Column(name = "criado_por", updatable = false)
    private String criadoPor;

    @CreatedDate
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @LastModifiedBy
    @Column(name = "ultima_modificacao_por")
    private String ultimaModificacaoPor;

    @LastModifiedDate
    @Column(name = "data_ultima_modificacao")
    private LocalDateTime dataUltimaModificacao;

    @Version
    @Column(name = "versao")
    private Integer versao;
}
