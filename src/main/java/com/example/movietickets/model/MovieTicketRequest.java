package com.example.movietickets.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieTicketRequest {
    private int transactionId;
    private List<MovieCustomer> customers;
}
