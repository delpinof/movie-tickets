package com.example.movietickets;

import com.example.movietickets.model.MovieCustomer;
import com.example.movietickets.model.MovieTicket;
import com.example.movietickets.model.MovieTicketRequest;
import com.example.movietickets.model.MovieTicketResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MovieTicketsDataProvider {

    private static final List<String> requestJson = new ArrayList<>();
    private static final List<String> responseJson = new ArrayList<>();
    private static final List<MovieTicketRequest> requestDto = new ArrayList<>();
    private static final List<MovieTicketResponse> responseDto = new ArrayList<>();

    public List<String> getRequestJson() throws IOException {
        if (requestJson.isEmpty()) {
            requestJson.add(getFileContent("/json/MovieTicketRequestExample1.json"));
            requestJson.add(getFileContent("/json/MovieTicketRequestExample2.json"));
            requestJson.add(getFileContent("/json/MovieTicketRequestExample3.json"));
        }
        return requestJson;
    }

    public List<String> getResponseJson() throws IOException {
        if (responseJson.isEmpty()) {
            responseJson.add(getFileContent("/json/MovieTicketResponseExample1.json"));
            responseJson.add(getFileContent("/json/MovieTicketResponseExample2.json"));
            responseJson.add(getFileContent("/json/MovieTicketResponseExample3.json"));
        }
        return responseJson;
    }

    public List<MovieTicketRequest> getRequestDto() {
        if (requestDto.isEmpty()) {
            requestDto.add(MovieTicketRequest.builder()
                    .transactionId(1)
                    .customers(List.of(
                            MovieCustomer.builder().name("John Smith").age(70).build(),
                            MovieCustomer.builder().name("Jane Doe").age(5).build(),
                            MovieCustomer.builder().name("Bob Doe").age(6).build()))
                    .build());
            requestDto.add(MovieTicketRequest.builder()
                    .transactionId(2)
                    .customers(List.of(
                            MovieCustomer.builder().name("Billy Kidd").age(36).build(),
                            MovieCustomer.builder().name("Zoe Daniels").age(3).build(),
                            MovieCustomer.builder().name("George White").age(8).build(),
                            MovieCustomer.builder().name("Tommy Anderson").age(9).build(),
                            MovieCustomer.builder().name("Joe Smith").age(17).build()))
                    .build());
            requestDto.add(MovieTicketRequest.builder()
                    .transactionId(3)
                    .customers(List.of(
                            MovieCustomer.builder().name("Jesse James").age(36).build(),
                            MovieCustomer.builder().name("Daniel Anderson").age(95).build(),
                            MovieCustomer.builder().name("Mary Jones").age(15).build(),
                            MovieCustomer.builder().name("Michelle Parker").age(10).build()))
                    .build());
        }
        return requestDto;
    }

    public static List<MovieTicketResponse> getResponseDto() {
        if (responseDto.isEmpty()) {
            responseDto.add(MovieTicketResponse.builder()
                    .transactionId(1)
                    .tickets(new TreeSet<>(Set.of(
                            MovieTicket.builder().ticketType("Senior").quantity(1).totalCost(17.5).build(),
                            MovieTicket.builder().ticketType("Children").quantity(2).totalCost(10.0).build()
                    )))
                    .totalCost(27.50)
                    .build());
            responseDto.add(MovieTicketResponse.builder()
                    .transactionId(1)
                    .tickets(new TreeSet<>(Set.of(
                            MovieTicket.builder().ticketType("Teen").quantity(1).totalCost(12.0).build(),
                            MovieTicket.builder().ticketType("Children").quantity(3).totalCost(11.25).build(),
                            MovieTicket.builder().ticketType("Adult").quantity(1).totalCost(25.0).build()
                    )))
                    .totalCost(48.25)
                    .build());
            responseDto.add(MovieTicketResponse.builder()
                    .transactionId(1)
                    .tickets(new TreeSet<>(Set.of(
                            MovieTicket.builder().ticketType("Senior").quantity(1).totalCost(17.5).build(),
                            MovieTicket.builder().ticketType("Teen").quantity(1).totalCost(12.0).build(),
                            MovieTicket.builder().ticketType("Children").quantity(1).totalCost(5.0).build(),
                            MovieTicket.builder().ticketType("Adult").quantity(1).totalCost(25.0).build()
                    )))
                    .totalCost(59.50)
                    .build());
        }
        return responseDto;
    }

    public String getFileContent(String fileName) throws IOException {
        return new String(MovieTicketsDataProvider.class.getResourceAsStream(fileName).readAllBytes());
    }
}
