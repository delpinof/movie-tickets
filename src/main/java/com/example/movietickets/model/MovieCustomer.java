package com.example.movietickets.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieCustomer {
    private String name;
    private int age;
}
