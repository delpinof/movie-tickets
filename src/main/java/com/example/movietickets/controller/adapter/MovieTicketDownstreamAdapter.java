package com.example.movietickets.controller.adapter;

import com.example.movietickets.api.model.MovieCustomer;
import com.example.movietickets.api.model.MovieTicketRequest;
import com.example.movietickets.service.model.MovieTicketInputDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Adapter to decouple the controller and service layer input/output.
 * So that the JSON POJOs and the DTO can change independently.
 */
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
