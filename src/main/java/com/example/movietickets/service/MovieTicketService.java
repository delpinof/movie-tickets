package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscount;
import com.example.movietickets.service.config.MovieTicketPricesConfiguration;
import com.example.movietickets.service.config.MovieTicketType;
import com.example.movietickets.service.model.MovieTicketInputDto;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.example.movietickets.service.model.QuantityCost;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieTicketService implements MovieTicketProcessor {

    @Autowired
    private final MovieTicketPricesConfiguration configuration;

    @Override
    public MovieTicketTypePriceDto process(MovieTicketInputDto inputDto) {
        MovieTicketTypePriceDto result = new MovieTicketTypePriceDto();
        for (int age : inputDto.getCustomersAge()) {
            MovieTicketType movieTicketType = getMovieTicketType(age);
            QuantityCost quantityCost = addTicketCost(result.getTickets(), movieTicketType);
            applyDiscount(movieTicketType, quantityCost);
        }
        return result;
    }

    private MovieTicketType getMovieTicketType(int age) {
        return configuration.getPrices()
                .stream()
                .filter(e -> age >= e.getAge())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ticket Type not found for age"));
    }

    private QuantityCost addTicketCost(Map<String, QuantityCost> tickets, MovieTicketType movieTicketType) {
        QuantityCost quantityCost = tickets.get(movieTicketType.getName());
        if (Objects.isNull(quantityCost)) {
            quantityCost = QuantityCost.builder()
                    .quantity(1)
                    .totalCost(movieTicketType.getPrice())
                    .build();
            tickets.put(movieTicketType.getName(), quantityCost);
        } else {
            quantityCost.setQuantity(quantityCost.getQuantity() + 1);
            quantityCost.setTotalCost(quantityCost.getTotalCost() + movieTicketType.getPrice());
        }
        return quantityCost;
    }

    private void applyDiscount(MovieTicketType movieTicketType, QuantityCost quantityCost) {
        MovieTicketDiscount movieTicketDiscount = movieTicketType.getDiscount();
        if (movieTicketDiscount != null &&
                quantityCost.getQuantity() >= movieTicketDiscount.getDiscountFor()) {
            double discount = quantityCost.getTotalCost() * movieTicketDiscount.getDiscountAmount();
            quantityCost.setTotalCost(quantityCost.getTotalCost() - discount);
        }
    }

}
