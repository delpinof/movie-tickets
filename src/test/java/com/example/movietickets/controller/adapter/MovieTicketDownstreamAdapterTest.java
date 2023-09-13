package com.example.movietickets.controller.adapter;

import com.example.movietickets.api.model.MovieCustomer;
import com.example.movietickets.api.model.MovieTicketRequest;
import com.example.movietickets.service.model.MovieTicketInputDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class MovieTicketDownstreamAdapterTest {
    private final MovieTicketDownstreamAdapter sut = new MovieTicketDownstreamAdapter();

    @ParameterizedTest
    @MethodSource("testDownstreamAdapterDataSource")
    void testDownstreamAdapter(MovieTicketRequest movieTicketRequest, List<Integer> expectedList) {
        MovieTicketInputDto actualResult = sut.apply(movieTicketRequest);
        assertThat(actualResult.getCustomersAge()).isEqualTo(expectedList);
    }

    public static Stream<Arguments> testDownstreamAdapterDataSource() {
        MovieTicketRequest movieTicketRequest1 = MovieTicketRequest.builder()
                .transactionId(1)
                .customers(List.of(
                        MovieCustomer.builder().name("John Smith").age(70).build(),
                        MovieCustomer.builder().name("Jane Doe").age(5).build(),
                        MovieCustomer.builder().name("Bob Doe").age(6).build()))
                .build();
        MovieTicketRequest movieTicketRequest2 = MovieTicketRequest.builder()
                .transactionId(2)
                .customers(List.of(
                        MovieCustomer.builder().name("Billy Kidd").age(36).build(),
                        MovieCustomer.builder().name("Zoe Daniels").age(3).build(),
                        MovieCustomer.builder().name("George White").age(8).build(),
                        MovieCustomer.builder().name("Tommy Anderson").age(9).build(),
                        MovieCustomer.builder().name("Joe Smith").age(17).build()))
                .build();
        MovieTicketRequest movieTicketRequest3 = MovieTicketRequest.builder()
                .transactionId(3)
                .customers(List.of(
                        MovieCustomer.builder().name("Jesse James").age(36).build(),
                        MovieCustomer.builder().name("Daniel Anderson").age(95).build(),
                        MovieCustomer.builder().name("Mary Jones").age(15).build(),
                        MovieCustomer.builder().name("Michelle Parker").age(10).build()))
                .build();

        return Stream.of(
                Arguments.of(movieTicketRequest1, List.of(70, 5, 6)),
                Arguments.of(movieTicketRequest2, List.of(36, 3, 8, 9, 17)),
                Arguments.of(movieTicketRequest3, List.of(36, 95, 15, 10))
        );
    }
}