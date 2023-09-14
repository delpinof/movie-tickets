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

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieTicketService implements MovieTicketProcessor {

    @Autowired
    private final MovieTicketPricesConfiguration configuration;

    @Override
    public MovieTicketTypePriceDto process(MovieTicketInputDto inputDto) {
        Map<MovieTicketType, Long> ticketTypeCountMap = inputDto.getCustomersAge()
                .stream()
                .map(this::getMovieTicketType)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        MovieTicketTypePriceDto result = new MovieTicketTypePriceDto();
        for (MovieTicketType movieTicketType : ticketTypeCountMap.keySet()) {
            long quantity = ticketTypeCountMap.get(movieTicketType);
            double totalCost = movieTicketType.getPrice() * quantity;
            //apply discount if applicable
            MovieTicketDiscount movieTicketDiscount = movieTicketType.getDiscount();
            if (nonNull(movieTicketDiscount) && quantity >= movieTicketDiscount.getDiscountFor()) {
                totalCost -= totalCost * movieTicketDiscount.getDiscountAmount();
            }
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
