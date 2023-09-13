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

import java.util.Optional;
import java.util.function.Consumer;

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

            result.getTickets().merge(movieTicketPrice.getName(),
                    QuantityCost.builder().quantity(1).totalCost(movieTicketPrice.getPrice()).build(),
                    (oldV, newV) -> {
                        newV.setQuantity(oldV.getQuantity() + 1);
                        newV.setTotalCost(oldV.getTotalCost() + movieTicketPrice.getPrice());
                        Optional.ofNullable(movieTicketPrice.getDiscount())
                                .ifPresent(movieTicketDiscount -> {
                                    if (newV.getQuantity() >= movieTicketDiscount.getDiscountFor()) {
                                        double discount = newV.getTotalCost() * movieTicketDiscount.getDiscountAmount();
                                        newV.setTotalCost(newV.getTotalCost() - discount);
                                    }
                                });
                        return newV;
                    });
        }
        return result;
    }

    static class MovieTicketDiscountConsumer implements Consumer<MovieTicketDiscount> {

        private final QuantityCost quantityCost;

        public MovieTicketDiscountConsumer(QuantityCost quantityCost) {
            this.quantityCost = quantityCost;
        }

        @Override
        public void accept(MovieTicketDiscount movieTicketDiscount) {
            if (quantityCost.getQuantity() >= movieTicketDiscount.getDiscountFor()) {
                double discount = quantityCost.getTotalCost() * movieTicketDiscount.getDiscountAmount();
                quantityCost.setTotalCost(quantityCost.getTotalCost() - discount);
            }
        }
    }
}
