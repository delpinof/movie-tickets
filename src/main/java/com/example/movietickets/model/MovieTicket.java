package com.example.movietickets.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieTicket implements Comparable<MovieTicket> {
    private MovieTicketType ticketType;
    private int quantity;
    private double totalCost;

    public int compareTo(MovieTicket movieTicket) {
        return this.getTicketType().compareTo(movieTicket.getTicketType());
    }
}
