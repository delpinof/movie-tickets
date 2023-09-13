package com.example.movietickets.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class MovieTicketRequestTest {

    @Autowired
    private ObjectMapper objectMapper;

    private static String movieTicketRequestJson1;
    private static String movieTicketRequestJson2;
    private static String movieTicketRequestJson3;

    private static MovieTicketRequest movieTicketRequestDto1;
    private static MovieTicketRequest movieTicketRequestDto2;
    private static MovieTicketRequest movieTicketRequestDto3;


    @BeforeAll
    public static void setUp() throws IOException {

        movieTicketRequestJson1 = new String(MovieTicketRequestTest.class
                .getResourceAsStream("/json/MovieTicketRequestExample1.json")
                .readAllBytes());
        movieTicketRequestDto1 = MovieTicketRequest.builder()
                .transactionId(1)
                .customers(List.of(
                        MovieCustomer.builder().name("John Smith").age(70).build(),
                        MovieCustomer.builder().name("Jane Doe").age(5).build(),
                        MovieCustomer.builder().name("Bob Doe").age(6).build()))
                .build();

        movieTicketRequestJson2 = new String(MovieTicketRequestTest.class
                .getResourceAsStream("/json/MovieTicketRequestExample2.json")
                .readAllBytes());
        movieTicketRequestDto2 = MovieTicketRequest.builder()
                .transactionId(2)
                .customers(List.of(
                        MovieCustomer.builder().name("Billy Kidd").age(36).build(),
                        MovieCustomer.builder().name("Zoe Daniels").age(3).build(),
                        MovieCustomer.builder().name("George White").age(8).build(),
                        MovieCustomer.builder().name("Tommy Anderson").age(9).build(),
                        MovieCustomer.builder().name("Joe Smith").age(17).build()))
                .build();

        movieTicketRequestJson3 = new String(MovieTicketRequestTest.class
                .getResourceAsStream("/json/MovieTicketRequestExample3.json")
                .readAllBytes());
        movieTicketRequestDto3 = MovieTicketRequest.builder()
                .transactionId(3)
                .customers(List.of(
                        MovieCustomer.builder().name("Jesse James").age(36).build(),
                        MovieCustomer.builder().name("Daniel Anderson").age(95).build(),
                        MovieCustomer.builder().name("Mary Jones").age(15).build(),
                        MovieCustomer.builder().name("Michelle Parker").age(10).build()))
                .build();
    }

    @ParameterizedTest
    @MethodSource("movieTicketRequestData")
    public void serializeTest(String expectedJsonRequest, MovieTicketRequest movieTicketRequestDto) throws IOException {
        String actualSerializedRequest = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(movieTicketRequestDto);
        assertThat(actualSerializedRequest).isEqualTo(expectedJsonRequest);
    }

    @ParameterizedTest
    @MethodSource("movieTicketRequestData")
    public void deserializeTest(String jsonRequest, MovieTicketRequest expectedRequestDto) throws IOException {
        MovieTicketRequest actualRequest = objectMapper.readValue(jsonRequest, MovieTicketRequest.class);
        assertThat(actualRequest).isEqualTo(expectedRequestDto);
    }

    public static Stream<Arguments> movieTicketRequestData() {
        return Stream.of(
                Arguments.of(movieTicketRequestJson1, movieTicketRequestDto1),
                Arguments.of(movieTicketRequestJson2, movieTicketRequestDto2),
                Arguments.of(movieTicketRequestJson3, movieTicketRequestDto3)
        );
    }
}
