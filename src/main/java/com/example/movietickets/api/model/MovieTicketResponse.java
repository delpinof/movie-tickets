package com.example.movietickets.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.TreeSet;

@Data
@Builder
public class MovieTicketResponse {
    private int transactionId;
    private TreeSet<MovieTicket> tickets; //Forcing TreeSet implementation for alphabetical order and avoid duplicates.
    private double totalCost;
}
