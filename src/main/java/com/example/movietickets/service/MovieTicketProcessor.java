package com.example.movietickets.service;

import com.example.movietickets.service.model.MovieTicketInputDto;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;

public interface MovieTicketProcessor {

    MovieTicketTypePriceDto process(MovieTicketInputDto inputDto);

}
