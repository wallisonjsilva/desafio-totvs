package br.com.desafio.totvs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Classe do swagger para versionamento da API
 */
@Configuration
public class OpenApiConfig {
    /**
     * Método responsável por gerar swagger da API
     * 
     * @return Retorna Swagger OpenAPI
     * @author Wallison Junior Cardoso Soares Silva
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API REST Desafio TOTVS")
                .version("v1")
                .description("Projeto com a solução para desafio TOTVS.")
                .termsOfService("")
                .license(
                    new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }

}
