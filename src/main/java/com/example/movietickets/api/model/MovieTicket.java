package com.example.movietickets.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class MovieTicket implements Comparable<MovieTicket> {
    private String ticketType;
    private long quantity;
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
