package com.example.movietickets.service;

import com.example.movietickets.model.MovieCustomer;
import com.example.movietickets.model.MovieTicket;
import com.example.movietickets.model.MovieTicketRequest;
import com.example.movietickets.model.MovieTicketResponse;
import com.example.movietickets.model.MovieTicketType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

@Service
public class MovieTicketService implements MovieTicketProcessor {
    @Override
    public MovieTicketResponse process(MovieTicketRequest request) {
        TreeSet<MovieTicket> tickets = new TreeSet<>();
        Map<MovieTicketType, Integer> ticketCount = new HashMap<>();
        for (MovieCustomer customer : request.getCustomers()) {
            MovieTicketType movieTicketType = getTicketType(customer.getAge());
            ticketCount.merge(movieTicketType, 1, Integer::sum);
        }

        double transactionCost = 0.0;
        for (MovieTicketType movieTicketType : ticketCount.keySet()) {
            int count = ticketCount.get(movieTicketType);
            double ticketTypeCost = getTicketPrice(movieTicketType) * count;
            ticketTypeCost -= ticketTypeCost * getDiscount(movieTicketType, count);
            tickets.add(MovieTicket.builder()
                    .ticketType(movieTicketType)
                    .quantity(count)
                    .totalCost(ticketTypeCost)
                    .build());
            transactionCost += ticketTypeCost;
        }

        return MovieTicketResponse.builder()
                .transactionId(request.getTransactionId())
                .tickets(tickets)
                .totalCost(transactionCost)
                .build();
    }

    private double getDiscount(MovieTicketType movieTicketType, int count) {
        double discount = 0.0;
        switch (movieTicketType) {
            case Senior -> discount = .30;
            case Children -> {
                if (count >= 3) {
                    discount = .25;
                }
            }
        }
        return discount;
    }

    public MovieTicketType getTicketType(int age) {
        if (age >= 65)
            return MovieTicketType.Senior;
        if (age >= 18)
            return MovieTicketType.Adult;
        if (age >= 11)
            return MovieTicketType.Teen;
        else
            return MovieTicketType.Children;
    }

    public double getTicketPrice(MovieTicketType ticketType) {
        return switch (ticketType) {
            case Adult, Senior -> 25;
            case Teen -> 12;
            case Children -> 5;
        };
    }
}
