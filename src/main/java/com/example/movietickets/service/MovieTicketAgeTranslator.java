package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketPricesConfiguration;
import com.example.movietickets.service.config.MovieTicketType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieTicketAgeTranslator implements Function<Integer, MovieTicketType> {

    @Autowired
    private final MovieTicketPricesConfiguration configuration;

    @Override
    public MovieTicketType apply(Integer age) {
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
