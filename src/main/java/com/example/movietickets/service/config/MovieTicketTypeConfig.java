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

    /**
     * Method use by the Collection API to order the elements.
     * It's intended to use decreasing order by age to simplify the algorithm that identifies the age range of a customer.
     * @param anotherPrice the object to be compared.
     * @return inverse behavior than Integer.compareTo() method.
     */
    @Override
    public int compareTo(MovieTicketTypeConfig anotherPrice) {
        return anotherPrice.getAge().compareTo(this.getAge());
    }
}
