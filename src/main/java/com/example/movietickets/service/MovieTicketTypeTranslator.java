package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscount;
import com.example.movietickets.service.config.MovieTicketType;
import com.example.movietickets.service.model.QuantityCost;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.nonNull;

@Component
public class MovieTicketTypeTranslator implements Function<Map.Entry<MovieTicketType, Long>, Map.Entry<String, QuantityCost>> {
    @Override
    public Map.Entry<String, QuantityCost> apply(Map.Entry<MovieTicketType, Long> movieTicketTypeIntegerEntry) {
        MovieTicketType movieTicketType = movieTicketTypeIntegerEntry.getKey();
        long quantity = movieTicketTypeIntegerEntry.getValue();
        double totalCost = movieTicketType.getPrice() * quantity;
        //apply discount if applicable
        MovieTicketDiscount movieTicketDiscount = movieTicketType.getDiscount();
        if (nonNull(movieTicketDiscount) && quantity >= movieTicketDiscount.getDiscountFor()) {
            totalCost -= totalCost * movieTicketDiscount.getDiscountAmount();
        }
        return new AbstractMap.SimpleEntry<>(movieTicketType.getName(), new QuantityCost(quantity, totalCost));
    }
}
