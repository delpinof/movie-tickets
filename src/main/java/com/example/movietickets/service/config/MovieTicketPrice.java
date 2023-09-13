package com.example.movietickets.service.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieTicketPrice implements Comparable<MovieTicketPrice> {
    private String name;
    private double price;
    private Integer age;
    private MovieTicketDiscount discount;

    @Override
    public int compareTo(MovieTicketPrice anotherPrice) {
        return this.getAge().compareTo(anotherPrice.getAge());
    }
}
