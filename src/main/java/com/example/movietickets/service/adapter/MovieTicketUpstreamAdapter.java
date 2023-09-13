package com.example.movietickets.service.adapter;

import com.example.movietickets.model.MovieTicket;
import com.example.movietickets.model.MovieTicketResponse;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.example.movietickets.service.model.QuantityCost;
import org.springframework.stereotype.Component;

import java.util.TreeSet;
import java.util.function.Function;

@Component
public class MovieTicketUpstreamAdapter implements Function<MovieTicketTypePriceDto, MovieTicketResponse.MovieTicketResponseBuilder> {
    @Override
    public MovieTicketResponse.MovieTicketResponseBuilder apply(MovieTicketTypePriceDto movieTicketTypePriceDto) {
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
                .totalCost(totalCost);
    }
}
