package com.example.movietickets.service;

import com.example.movietickets.model.MovieTicketRequest;
import com.example.movietickets.model.MovieTicketResponse;

public interface MovieTicketProcessor {

    MovieTicketResponse process(MovieTicketRequest request);

}
