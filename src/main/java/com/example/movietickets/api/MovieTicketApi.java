package com.example.movietickets.api;

import com.example.movietickets.api.model.MovieTicketRequest;
import com.example.movietickets.api.model.MovieTicketResponse;

public interface MovieTicketApi {

    String MOVIE_TICKET_TRANSACTION = "/api/v1/movie/tickets/transaction";

    MovieTicketResponse ticketTransaction(MovieTicketRequest request);
}
