package com.example.movietickets;

import com.example.movietickets.controller.MovieTicketController;
import com.example.movietickets.service.config.MovieTicketPricesConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MovieTicketsApplicationTests {

    @Autowired
    private MovieTicketController movieTicketController;

    @Autowired
    private MovieTicketPricesConfiguration pricesConfiguration;

    @Test
    void contextLoads() {
        assertThat(movieTicketController).isNotNull();
        assertThat(pricesConfiguration).isNotNull();
        assertThat(pricesConfiguration.getPrices()).isNotEmpty();
    }

}
