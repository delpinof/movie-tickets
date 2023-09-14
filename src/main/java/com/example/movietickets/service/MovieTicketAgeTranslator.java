package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketPricesConfiguration;
import com.example.movietickets.service.config.MovieTicketTypeConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Extract the correct Ticket Type-Price configuration based on the age.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MovieTicketAgeTranslator implements Function<Integer, MovieTicketTypeConfig> {

    @Autowired
    private final MovieTicketPricesConfiguration configuration;

    @Override
    public MovieTicketTypeConfig apply(Integer age) {
        return configuration.getPrices()
                .stream()
                .filter(e -> age >= e.getAge())
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Ticket type not found for age: {}", age);
                    return new RuntimeException("Ticket Type not found");
                });
    }
}
