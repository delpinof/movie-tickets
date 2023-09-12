package com.example.movietickets.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.TreeSet;

@Getter
@EqualsAndHashCode
@Builder
public class MovieTicketResponse {
    private int transactionId;
    private TreeSet<MovieTicket> tickets; //Forcing TreeSet implementation for alphabetical order and avoid duplicates.
    private double totalCost;
}
