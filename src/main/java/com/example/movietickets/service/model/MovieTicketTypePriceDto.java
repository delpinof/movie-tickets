package com.example.movietickets.service.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MovieTicketTypePriceDto {
    private final Map<String, QuantityCost> tickets = new HashMap<>();

}
