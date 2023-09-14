package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscountConfig;
import com.example.movietickets.service.config.MovieTicketTypeConfig;
import com.example.movietickets.service.model.QuantityCost;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.nonNull;

/**
 * Function that receives a map entry of the Ticket type-price configuration as key and its quantity as value,
 * computes the totalCost and apply discount if applicable,
 * then return a new map entry of the String ticket type as key and the quantity-cost as value.
 */
@Component
public class MovieTicketTypeTranslator implements Function<Map.Entry<MovieTicketTypeConfig, Long>, Map.Entry<String, QuantityCost>> {
    @Override
    public Map.Entry<String, QuantityCost> apply(Map.Entry<MovieTicketTypeConfig, Long> movieTicketTypeQuantityEntry) {
        MovieTicketTypeConfig movieTicketTypeConfig = movieTicketTypeQuantityEntry.getKey();
        long quantity = movieTicketTypeQuantityEntry.getValue();
        double totalCost = movieTicketTypeConfig.getPrice() * quantity;
        //apply discount if applicable
        MovieTicketDiscountConfig movieTicketDiscountConfig = movieTicketTypeConfig.getDiscount();
        if (nonNull(movieTicketDiscountConfig) && quantity >= movieTicketDiscountConfig.getDiscountFor()) {
            totalCost -= totalCost * movieTicketDiscountConfig.getDiscountAmount();
        }
        return new AbstractMap.SimpleEntry<>(movieTicketTypeConfig.getName(), new QuantityCost(quantity, totalCost));
    }
}
