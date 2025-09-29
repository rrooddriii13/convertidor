package es.curso.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ResourceInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        copyNoImageToStatic();
    }
    
    private void copyNoImageToStatic() {
        try {
            // Crear directorio si no existe
            Path staticImagesDir = Paths.get("src/main/resources/static/imagenes");
            if (!Files.exists(staticImagesDir)) {
                Files.createDirectories(staticImagesDir);
            }
            
            // Copiar no-image.png desde images a static/imagenes
            ClassPathResource sourceResource = new ClassPathResource("images/no-image.png");
            Path targetPath = staticImagesDir.resolve("no-image.png");
            
            if (sourceResource.exists() && !Files.exists(targetPath)) {
                try (InputStream inputStream = sourceResource.getInputStream()) {
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Imagen no-image.png copiada exitosamente a static/imagenes/");
                }
            }
        } catch (IOException e) {
            System.err.println("Error copiando imagen por defecto: " + e.getMessage());
        }
    }
}