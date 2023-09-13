package com.example.movietickets.service.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieTicketDiscount {
    private int discountFor;
    private double discountAmount;
}
