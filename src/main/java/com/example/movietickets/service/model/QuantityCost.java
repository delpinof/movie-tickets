package com.example.movietickets.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuantityCost {
    private long quantity;
    private double totalCost;
}
