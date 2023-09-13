package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscount;
import com.example.movietickets.service.config.MovieTicketPrice;
import com.example.movietickets.service.config.MovieTicketPricesConfiguration;
import com.example.movietickets.service.model.MovieTicketInputDto;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.example.movietickets.service.model.QuantityCost;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            MovieTicketPrice movieTicketPrice = configuration.getPrices()
                    .stream()
                    .filter(e -> age >= e.getAge())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Ticket Type not found for age"));

            QuantityCost quantityCost = result.getTickets().get(movieTicketPrice.getName());
            if (Objects.isNull(quantityCost)) {
                quantityCost = QuantityCost.builder()
                        .quantity(1)
                        .totalCost(movieTicketPrice.getPrice())
                        .build();
                result.getTickets().put(movieTicketPrice.getName(), quantityCost);
            } else {
                quantityCost.setQuantity(quantityCost.getQuantity() + 1);
                quantityCost.setTotalCost(quantityCost.getTotalCost() + movieTicketPrice.getPrice());
            }

            MovieTicketDiscount movieTicketDiscount = movieTicketPrice.getDiscount();
            if (movieTicketDiscount != null &&
                    quantityCost.getQuantity() >= movieTicketDiscount.getDiscountFor()) {
                double discount = quantityCost.getTotalCost() * movieTicketDiscount.getDiscountAmount();
                quantityCost.setTotalCost(quantityCost.getTotalCost() - discount);
            }

        }
        return result;
    }

}
