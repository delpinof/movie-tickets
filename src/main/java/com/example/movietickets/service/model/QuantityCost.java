package com.example.movietickets.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuantityCost {
    private int quantity;
    private double totalCost;
}
