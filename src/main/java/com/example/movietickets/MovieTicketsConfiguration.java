package com.example.movietickets;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieTicketsConfiguration {
    @Bean
    public OpenAPI movieTicketsOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("movie-tickets API")
                        .description("A movie ticket price calculator by age and ticket type.")
                        .version("1.0"));
    }
}
