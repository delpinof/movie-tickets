package com.example.movietickets.service.adapter;

import com.example.movietickets.model.MovieTicket;
import com.example.movietickets.model.MovieTicketResponse;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.example.movietickets.service.model.QuantityCost;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class MovieTicketUpstreamAdapterTest {

    private final MovieTicketUpstreamAdapter sut = new MovieTicketUpstreamAdapter();

    @ParameterizedTest
    @MethodSource("testUpstreamAdapterDataSource")
    void testUpstreamAdapter(MovieTicketTypePriceDto dto, MovieTicketResponse expectedResponse) {
        MovieTicketResponse actualResponse = sut.apply(dto);
        actualResponse.setTransactionId(expectedResponse.getTransactionId());
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    public static Stream<Arguments> testUpstreamAdapterDataSource() {
        MovieTicketTypePriceDto dto1 = new MovieTicketTypePriceDto();
        dto1.getTickets().put("Children", new QuantityCost(2, 10.0));
        dto1.getTickets().put("Senior", new QuantityCost(1, 17.50));
        MovieTicketResponse response1 = MovieTicketResponse.builder()
                .transactionId(1)
                .tickets(new TreeSet<>(Set.of(
                        MovieTicket.builder().ticketType("Senior").quantity(1).totalCost(17.5).build(),
                        MovieTicket.builder().ticketType("Children").quantity(2).totalCost(10.0).build()
                )))
                .totalCost(27.50)
                .build();

        MovieTicketTypePriceDto dto2 = new MovieTicketTypePriceDto();
        dto2.getTickets().put("Children", new QuantityCost(3, 11.25));
        dto2.getTickets().put("Adult", new QuantityCost(1, 25.0));
        dto2.getTickets().put("Teen", new QuantityCost(1, 12.0));
        MovieTicketResponse response2 = MovieTicketResponse.builder()
                .transactionId(2)
                .tickets(new TreeSet<>(Set.of(
                        MovieTicket.builder().ticketType("Teen").quantity(1).totalCost(12.0).build(),
                        MovieTicket.builder().ticketType("Children").quantity(3).totalCost(11.25).build(),
                        MovieTicket.builder().ticketType("Adult").quantity(1).totalCost(25.0).build()
                )))
                .totalCost(48.25)
                .build();

        MovieTicketTypePriceDto dto3 = new MovieTicketTypePriceDto();
        dto3.getTickets().put("Children", new QuantityCost(1, 5.0));
        dto3.getTickets().put("Adult", new QuantityCost(1, 25.0));
        dto3.getTickets().put("Teen", new QuantityCost(1, 12.0));
        dto3.getTickets().put("Senior", new QuantityCost(1, 17.5));
        MovieTicketResponse response3 = MovieTicketResponse.builder()
                .transactionId(3)
                .tickets(new TreeSet<>(Set.of(
                        MovieTicket.builder().ticketType("Senior").quantity(1).totalCost(17.5).build(),
                        MovieTicket.builder().ticketType("Teen").quantity(1).totalCost(12.0).build(),
                        MovieTicket.builder().ticketType("Children").quantity(1).totalCost(5.0).build(),
                        MovieTicket.builder().ticketType("Adult").quantity(1).totalCost(25.0).build()
                )))
                .totalCost(59.50)
                .build();
        return Stream.of(
                Arguments.of(dto1, response1),
                Arguments.of(dto2, response2),
                Arguments.of(dto3, response3)
        );
    }
}