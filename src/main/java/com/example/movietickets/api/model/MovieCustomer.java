package com.example.movietickets.api.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MovieCustomer {
    private String name;

    @Min(value = 1, message = "must be equal or greater than 1")
    @Max(value = 120, message = "must be equal or less than 120")
    private int age;
}
