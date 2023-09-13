package com.example.movietickets.controller;

import com.example.movietickets.api.MovieTicketApi;
import com.example.movietickets.model.MovieCustomer;
import com.example.movietickets.model.MovieTicketRequest;
import com.example.movietickets.model.MovieTicketResponse;
import com.example.movietickets.service.MovieTicketProcessor;
import com.example.movietickets.service.model.MovieTicketInputDto;
import com.example.movietickets.service.model.MovieTicketTypePriceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
@AutoConfigureMockMvc
public class MovieTicketControllerTest {
    @MockBean
    private MovieTicketProcessor movieTicketProcessor;
    @MockBean
    private Function<MovieTicketRequest, MovieTicketInputDto> downstreamAdapter;
    @MockBean
    private Function<MovieTicketTypePriceDto, MovieTicketResponse> upstreamAdapter;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("jsonDataProvider")
    public void sendMovieTicketRequest_returnExpectedResponse(String requestFileName, String responseFileName) throws Exception {
        String movieTicketRequest = new String(MovieTicketControllerTest.class
                .getResourceAsStream(requestFileName)
                .readAllBytes());

        String movieTicketResponse = new String(MovieTicketControllerTest.class
                .getResourceAsStream(responseFileName)
                .readAllBytes());

        MovieTicketResponse movieTicketServiceResponse = objectMapper.readValue(movieTicketResponse, MovieTicketResponse.class);

        when(downstreamAdapter.apply(any())).thenReturn(MovieTicketInputDto.builder().build());
        when(movieTicketProcessor.process(any())).thenReturn(new MovieTicketTypePriceDto());
        when(upstreamAdapter.apply(any())).thenReturn(movieTicketServiceResponse);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(MovieTicketApi.MOVIE_TICKET_TRANSACTION)
                .content(movieTicketRequest)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(movieTicketResponse));
    }

    public static Stream<Arguments> jsonDataProvider() {
        return Stream.of(
                Arguments.of("/json/MovieTicketRequestExample1.json", "/json/MovieTicketResponseExample1.json"),
                Arguments.of("/json/MovieTicketRequestExample2.json", "/json/MovieTicketResponseExample2.json"),
                Arguments.of("/json/MovieTicketRequestExample3.json", "/json/MovieTicketResponseExample3.json")
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testResponseStatus(MovieTicketRequest requestDto, ResultMatcher resultMatcherExpected) throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        when(downstreamAdapter.apply(any())).thenReturn(MovieTicketInputDto.builder().build());
        when(movieTicketProcessor.process(any())).thenReturn(new MovieTicketTypePriceDto());
        when(upstreamAdapter.apply(any())).thenReturn(MovieTicketResponse.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.post(MovieTicketApi.MOVIE_TICKET_TRANSACTION)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcherExpected);
    }

    public static Stream<Arguments> testResponseStatus() {
        MovieTicketRequest emptyRequest = MovieTicketRequest.builder().build();
        MovieTicketRequest wrongTransactionIdRequest = MovieTicketRequest.builder()
                .transactionId(-1)
                .build();
        MovieTicketRequest emptyCustomerListRequest = MovieTicketRequest.builder()
                .transactionId(5)
                .customers(new ArrayList<>())
                .build();
        MovieTicketRequest emptyCustomerRequest = MovieTicketRequest.builder()
                .transactionId(5)
                .customers(List.of(MovieCustomer.builder().build()))
                .build();
        MovieTicketRequest minRequiredRequest = MovieTicketRequest.builder()
                .transactionId(5)
                .customers(List.of(MovieCustomer.builder()
                        .age(1)
                        .build()))
                .build();
        return Stream.of(
                Arguments.of(emptyRequest, MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of(wrongTransactionIdRequest, MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of(emptyCustomerListRequest, MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of(emptyCustomerRequest, MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of(minRequiredRequest, MockMvcResultMatchers.status().isOk())
        );
    }

}
