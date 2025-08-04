package com.pluma.pluma.api.livro;

import com.pluma.pluma.modelo.livro.Livro;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroRequest {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String titulo;

    @NotBlank(message = "Autor é obrigatório")
    @Size(max = 100, message = "Autor deve ter no máximo 100 caracteres")
    private String autor;

    @NotBlank(message = "Gênero é obrigatório")
    @Size(max = 50, message = "Gênero deve ter no máximo 50 caracteres")
    private String genero;

    @Positive(message = "Preço deve ser positivo")
    @Digits(integer = 5, fraction = 2, message = "Preço deve ter no máximo 5 dígitos inteiros e 2 decimais")
    private Double preco;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 1000, message = "Descrição deve ter entre 10 e 1000 caracteres")
    private String descricao;

    @Min(value = 1000, message = "Ano inválido")
    @Max(value = 2023, message = "Ano não pode ser futuro")
    private int anoPublicacao;

    @Size(max = 255, message = "URL da capa muito longa")
    private String urlCapa;

    @Size(max = 255, message = "URL do arquivo PDF muito longa")
    private String urlArquivoPDF;

    @Positive(message = "Número de páginas deve ser positivo")
    private int numeroPaginas;

    @NotBlank(message = "Idioma é obrigatório")
    @Size(max = 30, message = "Idioma deve ter no máximo 30 caracteres")
    private String idioma;

    @PositiveOrZero(message = "Estoque não pode ser negativo")
    private int estoque;

    public Livro build() {
        return Livro.builder()
                .titulo(titulo)
                .autor(autor)
                .genero(genero)
                .preco(preco)
                .descricao(descricao)
                .anoPublicacao(anoPublicacao)
                .urlCapa(urlCapa != null && !urlCapa.isEmpty() ? urlCapa : 
                    "https://placehold.co/300x450/121212/C7A34F?font=playfair-display&text=" + titulo.replace(" ", "+"))
                .urlArquivoPDF(urlArquivoPDF)
                .numeroPaginas(numeroPaginas)
                .idioma(idioma)
                .estoque(estoque)
                .build();
    }
}