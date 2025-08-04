package com.pluma.pluma.api.livro;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    private final Path root = Paths.get("uploads");

    @GetMapping("/livro/{filename:.+}")
    public ResponseEntity<Resource> servirLivroDigital(@PathVariable String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, 
                               "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                        .body(resource);
            } else {
                throw new RuntimeException("Arquivo não encontrado: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao ler arquivo: " + e.getMessage());
        }
    }
}