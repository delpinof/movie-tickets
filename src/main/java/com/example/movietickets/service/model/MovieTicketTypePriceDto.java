package com.example.movietickets.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class MovieTicketTypePriceDto {
    private Map<String, QuantityCost> tickets;

}
