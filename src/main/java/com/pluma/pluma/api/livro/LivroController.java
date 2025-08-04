package com.pluma.pluma.api.livro;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluma.pluma.modelo.livro.Livro;
import com.pluma.pluma.modelo.livro.LivroService;
import com.pluma.pluma.service.FileStorageService;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LivroController {

    private final Logger logger = LoggerFactory.getLogger(LivroController.class);
    private final LivroService livroService;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    public LivroController(LivroService livroService, 
                         FileStorageService fileStorageService,
                         ObjectMapper objectMapper) {
        this.livroService = livroService;
        this.fileStorageService = fileStorageService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Livro> cadastrarLivro(
            @RequestPart("livro") String livroStr,
            @RequestPart(value = "arquivo", required = false) MultipartFile arquivo) throws IOException {
        
        Livro livro = objectMapper.readValue(livroStr, Livro.class);
        livro.setId(null); // Garante que o ID será gerado automaticamente
        
        if (arquivo != null && !arquivo.isEmpty()) {
            String nomeArquivo = fileStorageService.storeFile(arquivo);
            livro.setUrlArquivoPDF("/uploads/" + nomeArquivo);
        }
        
        Livro livroSalvo = livroService.salvarLivro(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Livro> atualizarLivro(
            @PathVariable Long id,
            @RequestPart("livro") String livroStr,
            @RequestPart(value = "arquivo", required = false) MultipartFile arquivo) throws IOException {
        
        Livro livroAtualizado = objectMapper.readValue(livroStr, Livro.class);
        
        if (arquivo != null && !arquivo.isEmpty()) {
            String nomeArquivo = fileStorageService.storeFile(arquivo);
            livroAtualizado.setUrlArquivoPDF("/uploads/" + nomeArquivo);
        }
        
        Livro resultado = livroService.atualizarLivro(id, livroAtualizado);
        return resultado != null ? ResponseEntity.ok(resultado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}