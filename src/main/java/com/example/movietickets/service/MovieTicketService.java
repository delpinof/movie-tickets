package com.example.movietickets.service;

import com.example.movietickets.service.config.MovieTicketType;
import com.example.movietickets.service.model.MovieTicketInputDto;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.example.movietickets.service.model.QuantityCost;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieTicketService implements MovieTicketProcessor {

    @Autowired
    private final Function<Integer, MovieTicketType> movieTicketAgeTranslator;

    @Autowired
    private final Function<Map.Entry<MovieTicketType, Long>, Map.Entry<String, QuantityCost>> movieTicketTypeTranslator;

    @Override
    public MovieTicketTypePriceDto process(MovieTicketInputDto inputDto) {
        Map<MovieTicketType, Long> ticketTypeCountMap = inputDto.getCustomersAge()
                .stream()
                .map(movieTicketAgeTranslator)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return MovieTicketTypePriceDto.builder()
                .tickets(ticketTypeCountMap.entrySet().stream()
                        .map(movieTicketTypeTranslator)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .build();
    }

}
