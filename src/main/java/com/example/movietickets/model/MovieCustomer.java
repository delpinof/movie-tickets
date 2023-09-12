package com.example.movietickets.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class MovieCustomer {
    private String name;
    private int age;
}
