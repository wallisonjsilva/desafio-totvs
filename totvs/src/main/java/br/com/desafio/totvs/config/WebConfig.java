package br.com.desafio.totvs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuração do CORS
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Método habilitar permissão do cors
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = "localhost:4200/";

        registry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(false);
    }
}
