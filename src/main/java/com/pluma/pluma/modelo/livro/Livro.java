package com.pluma.pluma.modelo.livro;

import com.pluma.pluma.util.entity.EntidadeAuditavel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro extends EntidadeAuditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private Double preco;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Column(name = "url_capa")
    private String urlCapa;

    @Column(name = "url_arquivopdf")
    private String urlArquivoPDF;

    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    @Column(name = "idioma")
    private String idioma;

    @Column(name = "estoque")
    private Integer estoque;
}
