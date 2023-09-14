package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscount;
import com.example.movietickets.service.config.MovieTicketType;
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
public class MovieTicketTypeTranslator implements Function<Map.Entry<MovieTicketType, Long>, Map.Entry<String, QuantityCost>> {
    @Override
    public Map.Entry<String, QuantityCost> apply(Map.Entry<MovieTicketType, Long> movieTicketTypeQuantityEntry) {
        MovieTicketType movieTicketType = movieTicketTypeQuantityEntry.getKey();
        long quantity = movieTicketTypeQuantityEntry.getValue();
        double totalCost = movieTicketType.getPrice() * quantity;
        //apply discount if applicable
        MovieTicketDiscount movieTicketDiscount = movieTicketType.getDiscount();
        if (nonNull(movieTicketDiscount) && quantity >= movieTicketDiscount.getDiscountFor()) {
            totalCost -= totalCost * movieTicketDiscount.getDiscountAmount();
        }
        return new AbstractMap.SimpleEntry<>(movieTicketType.getName(), new QuantityCost(quantity, totalCost));
    }
}
