package com.example.movietickets.service.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieTicketDiscountConfig {
    private int discountFor;
    private double discountAmount;
}
