package com.example.movietickets.service.config;

import lombok.Data;

@Data
public class MovieTicketDiscount {
    private int discountFor;
    private double discountAmount;
}
