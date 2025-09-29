package es.curso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configurar el manejo de imágenes desde static/imagenes
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("classpath:/static/imagenes/");
        
        // Configurar el manejo de imágenes desde resources/images
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/images/");
        
        // Configurar CSS y JS desde bootstrap
        registry.addResourceHandler("/bootstrap/**")
                .addResourceLocations("classpath:/bootstrap/");
        
        // Configurar CSS y JS desde static
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        
        // Para favicon y otros recursos estáticos generales
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
    }
}