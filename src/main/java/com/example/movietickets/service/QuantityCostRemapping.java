package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscount;
import com.example.movietickets.service.config.MovieTicketType;
import com.example.movietickets.service.model.QuantityCost;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class QuantityCostRemapping implements BiFunction<QuantityCost, QuantityCost, QuantityCost> {
    private MovieTicketType movieTicketType;
    private BiConsumer<MovieTicketDiscount, QuantityCost> movieTicketDiscountBiConsumer;

    public QuantityCostRemapping(MovieTicketType movieTicketType, BiConsumer<MovieTicketDiscount, QuantityCost> movieTicketDiscountBiConsumer) {
        this.movieTicketType = movieTicketType;
        this.movieTicketDiscountBiConsumer = movieTicketDiscountBiConsumer;
    }

    @Override
    public QuantityCost apply(QuantityCost oldV, QuantityCost newV) {
        newV.setQuantity(oldV.getQuantity() + 1);
        newV.setTotalCost(oldV.getTotalCost() + movieTicketType.getPrice());
        movieTicketDiscountBiConsumer.accept(movieTicketType.getDiscount(), newV);
        return newV;
    }
}
