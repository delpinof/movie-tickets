package com.example.movietickets.model;

import lombok.Data;

import java.util.List;

@Data
public class MovieTicketResponse {
    private int transactionId;
    private List<MovieTicket> tickets;
}
