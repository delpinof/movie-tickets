package com.example.movietickets.service.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieTicketType implements Comparable<MovieTicketType> {
    private String name;
    private double price;
    private Integer age;
    private MovieTicketDiscount discount;

    @Override
    public int compareTo(MovieTicketType anotherPrice) {
        return anotherPrice.getAge().compareTo(this.getAge());
    }
}
