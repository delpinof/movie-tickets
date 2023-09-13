package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscount;
import com.example.movietickets.service.config.MovieTicketPrice;
import com.example.movietickets.service.config.MovieTicketPricesConfiguration;
import com.example.movietickets.service.model.MovieTicketInputDto;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.example.movietickets.service.model.QuantityCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MovieTicketServiceTest {

    private MovieTicketProcessor sut;

    @BeforeEach
    public void setUp() {
        MovieTicketPricesConfiguration configuration = new MovieTicketPricesConfiguration();
        configuration.getPrices().add(MovieTicketPrice.builder()
                .name("Adult")
                .age(18)
                .price(25.0)
                .build());
        configuration.getPrices().add(MovieTicketPrice.builder()
                .name("Senior")
                .age(65)
                .price(25.0)
                .discount(MovieTicketDiscount.builder()
                        .discountFor(1)
                        .discountAmount(.30)
                        .build())
                .build());
        configuration.getPrices().add(MovieTicketPrice.builder()
                .name("Teen")
                .age(11)
                .price(12.0)
                .build());
        configuration.getPrices().add(MovieTicketPrice.builder()
                .name("Children")
                .age(0)
                .price(5.0)
                .discount(MovieTicketDiscount.builder()
                        .discountAmount(.25)
                        .discountFor(3)
                        .build())
                .build());
        sut = new MovieTicketService(configuration);
    }

    @Test
    void process() {
        MovieTicketTypePriceDto actualResult = sut.process(MovieTicketInputDto.builder()
                .customersAge(List.of(36, 3, 8, 9, 17))
                .build());
        MovieTicketTypePriceDto expectedResult = new MovieTicketTypePriceDto();
        Map<String, QuantityCost> tickets = new HashMap<>();
        tickets.put("Adult", QuantityCost.builder().quantity(1).totalCost(25.0).build());
        tickets.put("Children", QuantityCost.builder().quantity(3).totalCost(11.25).build());
        tickets.put("Teen", QuantityCost.builder().quantity(1).totalCost(12.0).build());
        expectedResult.setTickets(tickets);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}