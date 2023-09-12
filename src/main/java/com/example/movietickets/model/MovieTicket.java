package com.example.movietickets.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieTicket implements Comparable<MovieTicket> {
    private MovieTicketType ticketType;
    private int quantity;
    private double totalCost;

    public int compareTo(MovieTicket movieTicket) {
        return this.getTicketType().compareTo(movieTicket.getTicketType());
    }

    public boolean equals(Object o) {
        if (o instanceof MovieTicket movieTicket) {
            return this.getTicketType().equals(movieTicket.getTicketType());
        }
        return false;
    }

    public int hashCode() {
        return this.getTicketType().hashCode();
    }
}
