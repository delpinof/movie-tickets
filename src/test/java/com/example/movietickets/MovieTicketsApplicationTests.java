package com.example.movietickets;

import com.example.movietickets.controller.MovieTicketController;
import com.example.movietickets.service.config.MovieTicketPricesConfiguration;
import com.example.movietickets.service.config.MovieTicketTypeConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    /**
     * Verify prices configuration is in descending order by the age.
     */
    @Test
    void testPricesConfiguration() {
        List<Integer> ages = pricesConfiguration.getPrices().stream()
                .map(MovieTicketTypeConfig::getAge)
                .toList();

        for (int i = 1; i < ages.size(); i++) {
            assertThat(ages.get(i - 1)).isGreaterThan(ages.get(i));
        }
    }

}
