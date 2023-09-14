package com.example.movietickets.service.config;

import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.TreeSet;

@Component
@ConfigurationProperties("movie-tickets")
@Getter
@Builder
public class MovieTicketPricesConfiguration {

    private final TreeSet<MovieTicketType> prices; //Forcing TreeSet implementation for natural order and avoid duplicates.
}
