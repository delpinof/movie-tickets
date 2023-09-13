package com.example.movietickets.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MovieTicketInputDto {
    private List<Integer> customersAge;
}
