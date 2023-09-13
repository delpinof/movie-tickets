package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscount;
import com.example.movietickets.service.model.QuantityCost;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

import static java.util.Objects.nonNull;

@Component
public class MovieTicketDiscountBiConsumer implements BiConsumer<MovieTicketDiscount, QuantityCost> {

    @Override
    public void accept(MovieTicketDiscount movieTicketDiscount, QuantityCost quantityCost) {
        if (nonNull(movieTicketDiscount) && quantityCost.getQuantity() >= movieTicketDiscount.getDiscountFor()) {
            double discount = quantityCost.getTotalCost() * movieTicketDiscount.getDiscountAmount();
            quantityCost.setTotalCost(quantityCost.getTotalCost() - discount);
        }
    }
}
