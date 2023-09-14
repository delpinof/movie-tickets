package com.example.movietickets.service.config;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class MovieTicketTypeConfig implements Comparable<MovieTicketTypeConfig> {
    private String name;
    private double price;
    private Integer age;
    private MovieTicketDiscountConfig discount;

    @Override
    public int compareTo(MovieTicketTypeConfig anotherPrice) {
        return anotherPrice.getAge().compareTo(this.getAge());
    }
}
