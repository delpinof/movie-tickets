package com.example.movietickets.controller.adapter;

import com.example.movietickets.api.model.MovieTicket;
import com.example.movietickets.api.model.MovieTicketResponse;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.example.movietickets.service.model.QuantityCost;
import org.springframework.stereotype.Component;

import java.util.TreeSet;
import java.util.function.Function;

/**
 * Adapter to decouple the controller and service layer input/output.
 * So that the JSON POJOs and the DTO can change independently.
 */
@Component
public class MovieTicketUpstreamAdapter implements Function<MovieTicketTypePriceDto, MovieTicketResponse> {
    @Override
    public MovieTicketResponse apply(MovieTicketTypePriceDto movieTicketTypePriceDto) {
        TreeSet<MovieTicket> tickets = new TreeSet<>();
        double totalCost = 0.0;
        for (String ticketType : movieTicketTypePriceDto.getTickets().keySet()) {
            QuantityCost quantityCost = movieTicketTypePriceDto.getTickets().get(ticketType);
            tickets.add(MovieTicket.builder()
                    .ticketType(ticketType)
                    .quantity(quantityCost.getQuantity())
                    .totalCost(quantityCost.getTotalCost())
                    .build());
            totalCost += quantityCost.getTotalCost();
        }
        return MovieTicketResponse.builder()
                .tickets(tickets)
                .totalCost(totalCost)
                .build();
    }
}
