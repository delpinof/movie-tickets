package com.example.movietickets.service.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MovieTicketTypePriceDto {
    private final Map<String, QuantityCost> tickets = new HashMap<>();

}
