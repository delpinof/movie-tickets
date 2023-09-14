package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketDiscount;
import com.example.movietickets.service.config.MovieTicketPricesConfiguration;
import com.example.movietickets.service.config.MovieTicketType;
import com.example.movietickets.service.model.MovieTicketInputDto;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.example.movietickets.service.model.QuantityCost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieTicketService implements MovieTicketProcessor {

    @Autowired
    private final MovieTicketPricesConfiguration configuration;

    @Override
    public MovieTicketTypePriceDto process(MovieTicketInputDto inputDto) {
        Map<MovieTicketType, Integer> ticketTypeCountMap = new HashMap<>();
        for (int age : inputDto.getCustomersAge()) {
            MovieTicketType movieTicketType = getMovieTicketType(age);
            ticketTypeCountMap.merge(movieTicketType, 1, Integer::sum);
        }
        MovieTicketTypePriceDto result = new MovieTicketTypePriceDto();
        for (MovieTicketType movieTicketType : ticketTypeCountMap.keySet()) {
            //calculate totalCost
            int quantity = ticketTypeCountMap.get(movieTicketType);
            double totalCost = movieTicketType.getPrice() * quantity;
            //apply discount if applicable
            MovieTicketDiscount movieTicketDiscount = movieTicketType.getDiscount();
            if (nonNull(movieTicketDiscount) && quantity >= movieTicketDiscount.getDiscountFor()) {
                totalCost -= totalCost * movieTicketDiscount.getDiscountAmount();
            }
            //add ticket type to result
            result.getTickets().put(movieTicketType.getName(), new QuantityCost(quantity, totalCost));
        }
        return result;
    }

    private MovieTicketType getMovieTicketType(int age) {
        return configuration.getPrices()
                .stream()
                .filter(e -> age >= e.getAge())
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Ticket type not found for age: {}", age);
                    return new RuntimeException("Ticket Type not found");
                });
    }

}
