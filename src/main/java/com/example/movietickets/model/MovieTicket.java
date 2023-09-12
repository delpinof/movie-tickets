package com.example.movietickets.model;

import lombok.Data;

@Data
public class MovieTicket {
    private MovieTicketType ticketType;
    private int quantity;
    private double totalCost;
}
