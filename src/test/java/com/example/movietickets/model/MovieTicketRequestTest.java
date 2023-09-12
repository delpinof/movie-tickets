package com.example.movietickets.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class MovieTicketRequestTest {

    @Autowired
    private ObjectMapper objectMapper;

    private static InputStream movieTicketRequestExample1Stream;
    private static InputStream movieTicketRequestExample2Stream;
    private static InputStream movieTicketRequestExample3Stream;

    private static MovieTicketRequest movieTicketRequest1Dto;
    private static MovieTicketRequest movieTicketRequest2Dto;
    private static MovieTicketRequest movieTicketRequest3Dto;


    @BeforeAll
    public static void setUp() {

        movieTicketRequestExample1Stream = MovieTicketRequestTest.class.getResourceAsStream("/json/MovieTicketRequestExample1.json");
        movieTicketRequest1Dto = MovieTicketRequest.builder()
                .transactionId(1)
                .customers(List.of(
                        MovieCustomer.builder().name("John Smith").age(70).build(),
                        MovieCustomer.builder().name("Jane Doe").age(5).build(),
                        MovieCustomer.builder().name("Bob Doe").age(6).build()))
                .build();


        movieTicketRequestExample2Stream = MovieTicketRequestTest.class.getResourceAsStream("/json/MovieTicketRequestExample2.json");
        movieTicketRequest2Dto = MovieTicketRequest.builder()
                .transactionId(2)
                .customers(List.of(
                        MovieCustomer.builder().name("Billy Kidd").age(36).build(),
                        MovieCustomer.builder().name("Zoe Daniels").age(3).build(),
                        MovieCustomer.builder().name("George White").age(8).build(),
                        MovieCustomer.builder().name("Tommy Anderson").age(9).build(),
                        MovieCustomer.builder().name("Joe Smith").age(17).build()))
                .build();

        movieTicketRequestExample3Stream = MovieTicketRequestTest.class.getResourceAsStream("/json/MovieTicketRequestExample3.json");
        movieTicketRequest3Dto = MovieTicketRequest.builder()
                .transactionId(3)
                .customers(List.of(
                        MovieCustomer.builder().name("Jesse James").age(36).build(),
                        MovieCustomer.builder().name("Daniel Anderson").age(95).build(),
                        MovieCustomer.builder().name("Mary Jones").age(15).build(),
                        MovieCustomer.builder().name("Michelle Parker").age(10).build()))
                .build();
    }

    @ParameterizedTest
    @MethodSource("serDeTestData")
    void serializeTest(InputStream expectedJsonRequest, MovieTicketRequest movieTicketRequest) throws IOException {
        String actualJsonRequest = objectMapper.writeValueAsString(movieTicketRequest);
        JsonNode actualTree = objectMapper.readTree(actualJsonRequest);
        JsonNode expectedTree = objectMapper.readTree(expectedJsonRequest);
        assertThat(actualTree).isEqualTo(expectedTree);
    }

    @ParameterizedTest
    @MethodSource("serDeTestData")
    void deserializeTest(InputStream jsonRequest, MovieTicketRequest expectedRequest) throws IOException {
        MovieTicketRequest actualRequest = objectMapper.readValue(jsonRequest, MovieTicketRequest.class);
        assertThat(actualRequest).isEqualTo(expectedRequest);
    }

    static Stream<Arguments> serDeTestData() {
        return Stream.of(
                Arguments.of(movieTicketRequestExample1Stream, movieTicketRequest1Dto),
                Arguments.of(movieTicketRequestExample2Stream, movieTicketRequest2Dto),
                Arguments.of(movieTicketRequestExample3Stream, movieTicketRequest3Dto)
        );
    }
}
