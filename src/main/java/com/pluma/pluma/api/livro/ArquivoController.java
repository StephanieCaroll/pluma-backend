package com.pluma.pluma.api.livro;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    private final Path fileStorageLocation;

    public ArquivoController(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @GetMapping("/livro/{filename:.+}")
    public ResponseEntity<Resource> servirLivroDigital(@PathVariable String filename) {
        try {
           
            String decodedFilename = java.net.URLDecoder.decode(filename, "UTF-8");
            Path file = fileStorageLocation.resolve(decodedFilename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, 
                               "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                        .body(resource);
            } else {
                throw new RuntimeException("Arquivo não encontrado: " + decodedFilename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler arquivo: " + e.getMessage());
        }
    }
}