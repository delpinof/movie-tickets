package com.example.movietickets.controller;

import com.example.movietickets.api.MovieTicketApi;
import com.example.movietickets.model.MovieTicketRequest;
import com.example.movietickets.model.MovieTicketResponse;
import com.example.movietickets.service.MovieTicketProcessor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MovieTicketController implements MovieTicketApi {

    @Autowired
    private final MovieTicketProcessor movieTicketProcessor;

    @Override
    @PostMapping(MOVIE_TICKET_TRANSACTION)
    public MovieTicketResponse ticketTransaction(@Valid @RequestBody MovieTicketRequest request) {
        log.info("{}", request);
        return movieTicketProcessor.process(request);
    }
}
