package com.example.movietickets.service.config;

import lombok.Data;

@Data
public class MovieTicketPrice implements Comparable<MovieTicketPrice> {
    private String name;
    private double price;
    private Integer age;
    private MovieTicketDiscount discount;

    @Override
    public int compareTo(MovieTicketPrice anotherPrice) {
        return anotherPrice.getAge().compareTo(this.getAge());
    }
}
