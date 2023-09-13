package com.example.movietickets.controller;

import com.example.movietickets.api.MovieTicketApi;
import com.example.movietickets.model.MovieTicketRequest;
import com.example.movietickets.model.MovieTicketResponse;
import com.example.movietickets.service.MovieTicketProcessor;
import com.example.movietickets.service.model.MovieTicketInputDto;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MovieTicketController implements MovieTicketApi {

    @Autowired
    private final MovieTicketProcessor movieTicketProcessor;

    @Autowired
    private final Function<MovieTicketRequest, MovieTicketInputDto> downstreamAdapter;

    @Autowired
    private final Function<MovieTicketTypePriceDto, MovieTicketResponse.MovieTicketResponseBuilder> upstreamAdapter;

    @Override
    @PostMapping(MOVIE_TICKET_TRANSACTION)
    public MovieTicketResponse ticketTransaction(@Valid @RequestBody MovieTicketRequest request) {
        log.debug("{}", request);
        return Optional.of(request)
                .map(downstreamAdapter)
                .map(movieTicketProcessor::process)
                .map(upstreamAdapter)
                .orElseThrow()
                .transactionId(request.getTransactionId())
                .build();
    }
}
