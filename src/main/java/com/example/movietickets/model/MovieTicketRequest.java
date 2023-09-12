package com.example.movietickets.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
@Builder
public class MovieTicketRequest {
    private int transactionId;
    private List<MovieCustomer> customers;
}
