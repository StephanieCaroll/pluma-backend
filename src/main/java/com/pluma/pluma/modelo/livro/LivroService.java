package com.pluma.pluma.modelo.livro;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public Livro salvarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public Livro atualizarLivro(Long id, Livro livroAtualizado) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setTitulo(livroAtualizado.getTitulo());
                    livro.setAutor(livroAtualizado.getAutor());
                    livro.setGenero(livroAtualizado.getGenero());
                    livro.setPreco(livroAtualizado.getPreco());
                    livro.setDescricao(livroAtualizado.getDescricao());
                    livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
                    livro.setUrlCapa(livroAtualizado.getUrlCapa());
                    livro.setUrlArquivoPDF(livroAtualizado.getUrlArquivoPDF());
                    livro.setNumeroPaginas(livroAtualizado.getNumeroPaginas());
                    livro.setIdioma(livroAtualizado.getIdioma());
                    livro.setEstoque(livroAtualizado.getEstoque());
                    
                    return livroRepository.save(livro);
                })
                .orElse(null);
    }

    public void deletarLivro(Long id) {
        livroRepository.deleteById(id);
    }
}