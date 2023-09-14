package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscount;
import com.example.movietickets.service.config.MovieTicketPricesConfiguration;
import com.example.movietickets.service.config.MovieTicketType;
import com.example.movietickets.service.model.MovieTicketInputDto;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.example.movietickets.service.model.QuantityCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MovieTicketServiceTest {

    private MovieTicketProcessor sut;

    @BeforeEach
    public void setUp() {
        MovieTicketPricesConfiguration configuration = MovieTicketPricesConfiguration.builder()
                .prices(new TreeSet<>())
                .build();
        configuration.getPrices().add(MovieTicketType.builder()
                .name("Adult")
                .age(18)
                .price(25.0)
                .build());
        configuration.getPrices().add(MovieTicketType.builder()
                .name("Senior")
                .age(65)
                .price(25.0)
                .discount(MovieTicketDiscount.builder()
                        .discountFor(1)
                        .discountAmount(.30)
                        .build())
                .build());
        configuration.getPrices().add(MovieTicketType.builder()
                .name("Teen")
                .age(11)
                .price(12.0)
                .build());
        configuration.getPrices().add(MovieTicketType.builder()
                .name("Children")
                .age(0)
                .price(5.0)
                .discount(MovieTicketDiscount.builder()
                        .discountAmount(.25)
                        .discountFor(3)
                        .build())
                .build());
        sut = new MovieTicketService(new MovieTicketAgeTranslator(configuration), new MovieTicketTypeTranslator());
    }

    @ParameterizedTest
    @MethodSource
    void testProcess(List<Integer> ageList, MovieTicketTypePriceDto expectedResult) {
        MovieTicketTypePriceDto actualResult = sut.process(MovieTicketInputDto.builder()
                .customersAge(ageList)
                .build());
        assertThat(actualResult.getTickets()).isEqualTo(expectedResult.getTickets());
    }

    public static Stream<Arguments> testProcess() {

        MovieTicketTypePriceDto childrenAndSenior = MovieTicketTypePriceDto.builder()
                .tickets(new HashMap<>())
                .build();
        childrenAndSenior.getTickets().put("Children", new QuantityCost(2, 10));
        childrenAndSenior.getTickets().put("Senior", new QuantityCost(1, 17.50));

        MovieTicketTypePriceDto threeChildren = MovieTicketTypePriceDto.builder()
                .tickets(new HashMap<>())
                .build();
        threeChildren.getTickets().put("Adult", new QuantityCost(1, 25.0));
        threeChildren.getTickets().put("Children", new QuantityCost(3, 11.25));
        threeChildren.getTickets().put("Teen", new QuantityCost(1, 12.0));

        MovieTicketTypePriceDto all = MovieTicketTypePriceDto.builder()
                .tickets(new HashMap<>())
                .build();
        all.getTickets().put("Adult", new QuantityCost(1, 25.0));
        all.getTickets().put("Children", new QuantityCost(1, 5));
        all.getTickets().put("Teen", new QuantityCost(1, 12.0));
        all.getTickets().put("Senior", new QuantityCost(1, 17.50));

        return Stream.of(
                Arguments.of(List.of(70, 5, 6), childrenAndSenior),
                Arguments.of(List.of(36, 3, 8, 9, 17), threeChildren),
                Arguments.of(List.of(36, 95, 15, 10), all)
        );
    }
}