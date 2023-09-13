package com.example.movietickets.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Component
@ConfigurationProperties("movie-tickets")
@Getter
@Setter
public class MovieTicketPricesConfiguration {

    private Set<MovieTicketPrice> prices = new TreeSet<>(Collections.reverseOrder());
}
