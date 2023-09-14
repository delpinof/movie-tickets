package com.example.movietickets;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieTicketsConfiguration {
    @Bean
    public OpenAPI movieTicketsOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("movie-tickets API")
                        .description("Movie tickets processor by ticket type")
                        .version("1.0"));
    }
}
