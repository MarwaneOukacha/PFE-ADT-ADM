package ma.adria.document_validation.administration.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
@OpenAPIDefinition(info = @Info(
        contact = @Contact(name = "Mohamed Benmansour", email = "mohamed.benmansour@adria-bt.com"),
        title = "ADT-Documents-validation Administration", version = "V1"
))
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI();

    }
}
