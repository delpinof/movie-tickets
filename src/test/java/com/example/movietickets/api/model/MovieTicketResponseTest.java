package com.example.movietickets.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;


@SpringBootTest
public class MovieTicketResponseTest {

    @Autowired
    private ObjectMapper objectMapper;

    private static String movieTicketResponseExample1Stream;
    private static String movieTicketResponseExample2Stream;
    private static String movieTicketResponseExample3Stream;

    private static MovieTicketResponse movieTicketResponse1Dto;
    private static MovieTicketResponse movieTicketResponse2Dto;
    private static MovieTicketResponse movieTicketResponse3Dto;

    @BeforeAll
    public static void setUp() throws IOException {

        movieTicketResponseExample1Stream = new String(MovieTicketResponseTest.class
                .getResourceAsStream("/json/MovieTicketResponseExample1.json")
                .readAllBytes());
        movieTicketResponse1Dto = MovieTicketResponse.builder()
                .transactionId(1)
                .tickets(new TreeSet<>(Set.of(
                        MovieTicket.builder().ticketType("Senior").quantity(1).totalCost(17.5).build(),
                        MovieTicket.builder().ticketType("Children").quantity(2).totalCost(10.0).build()
                )))
                .totalCost(27.50)
                .build();

        movieTicketResponseExample2Stream = new String(MovieTicketResponseTest.class
                .getResourceAsStream("/json/MovieTicketResponseExample2.json")
                .readAllBytes());
        movieTicketResponse2Dto = MovieTicketResponse.builder()
                .transactionId(2)
                .tickets(new TreeSet<>(Set.of(
                        MovieTicket.builder().ticketType("Teen").quantity(1).totalCost(12.0).build(),
                        MovieTicket.builder().ticketType("Children").quantity(3).totalCost(11.25).build(),
                        MovieTicket.builder().ticketType("Adult").quantity(1).totalCost(25.0).build()
                )))
                .totalCost(48.25)
                .build();

        movieTicketResponseExample3Stream = new String(MovieTicketResponseTest.class
                .getResourceAsStream("/json/MovieTicketResponseExample3.json")
                .readAllBytes());
        movieTicketResponse3Dto = MovieTicketResponse.builder()
                .transactionId(3)
                .tickets(new TreeSet<>(Set.of(
                        MovieTicket.builder().ticketType("Senior").quantity(1).totalCost(17.5).build(),
                        MovieTicket.builder().ticketType("Teen").quantity(1).totalCost(12.0).build(),
                        MovieTicket.builder().ticketType("Children").quantity(1).totalCost(5.0).build(),
                        MovieTicket.builder().ticketType("Adult").quantity(1).totalCost(25.0).build()
                )))
                .totalCost(59.50)
                .build();
    }

    @ParameterizedTest
    @MethodSource("movieTicketResponseData")
    public void serializeTest(String expectedJsonResponse, MovieTicketResponse movieTicketResponseDto) throws IOException {
        String serializedResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(movieTicketResponseDto);
        AssertionsForClassTypes.assertThat(serializedResponse).isEqualTo(expectedJsonResponse);
    }

    @ParameterizedTest
    @MethodSource("movieTicketResponseData")
    public void deserializeTest(String jsonResponse, MovieTicketResponse expectedResponseDto) throws IOException {
        MovieTicketResponse actualResponse = objectMapper.readValue(jsonResponse, MovieTicketResponse.class);
        AssertionsForClassTypes.assertThat(actualResponse).isEqualTo(expectedResponseDto);
    }

    public static Stream<Arguments> movieTicketResponseData() {
        return Stream.of(
                Arguments.of(movieTicketResponseExample1Stream, movieTicketResponse1Dto),
                Arguments.of(movieTicketResponseExample2Stream, movieTicketResponse2Dto),
                Arguments.of(movieTicketResponseExample3Stream, movieTicketResponse3Dto)
        );
    }
}
