package es.curso.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class ImageController {

    @GetMapping("/imagenes/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            // Intentar cargar desde static/imagenes
            Resource resource = new ClassPathResource("static/imagenes/" + filename);
            
            if (!resource.exists()) {
                // Si no existe, usar imagen por defecto
                resource = new ClassPathResource("static/imagenes/no-image.png");
                if (!resource.exists()) {
                    // Si no existe no-image.png, usar desde images
                    resource = new ClassPathResource("images/no-image.png");
                }
            }
            
            if (resource.exists()) {
                byte[] imageData = Files.readAllBytes(Path.of(resource.getURI()));
                
                // Determinar el tipo de contenido basado en la extensión
                MediaType mediaType = MediaType.IMAGE_PNG; // Por defecto PNG
                String fileName = resource.getFilename();
                if (fileName != null) {
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                        mediaType = MediaType.IMAGE_JPEG;
                    } else if (fileName.endsWith(".gif")) {
                        mediaType = MediaType.IMAGE_GIF;
                    } else if (fileName.endsWith(".ico")) {
                        mediaType = MediaType.valueOf("image/x-icon");
                    }
                }
                
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                        .body(imageData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + filename + " - " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}