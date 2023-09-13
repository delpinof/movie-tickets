package com.example.movietickets.service.adapter;

import com.example.movietickets.model.MovieCustomer;
import com.example.movietickets.model.MovieTicketRequest;
import com.example.movietickets.service.model.MovieTicketInputDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MovieTicketDownstreamAdapter implements Function<MovieTicketRequest, MovieTicketInputDto> {
    @Override
    public MovieTicketInputDto apply(MovieTicketRequest movieTicketRequest) {
        return MovieTicketInputDto.builder()
                .customersAge(movieTicketRequest
                        .getCustomers()
                        .stream()
                        .map(MovieCustomer::getAge)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
