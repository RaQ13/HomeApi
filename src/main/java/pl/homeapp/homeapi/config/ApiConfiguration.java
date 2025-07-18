package pl.homeapp.homeapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {

    @Bean
    public OpenAPI config() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Home API")
                .description("An api for manage and synchronize home devices connected to local network")
                .version("1.0"));

    }
}
