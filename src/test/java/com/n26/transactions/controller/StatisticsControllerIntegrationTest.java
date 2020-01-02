package com.n26.transactions.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.n26.transactions.model.Statistics;

/**
 * The Class StatisticsControllerIntegrationTest.
 * 
 * @author hrodriguez
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StatisticsControllerIntegrationTest {

    /** The client. */
    @Autowired
    private TestRestTemplate client;

    /** The Constant RESOURCE_URL. */
    private static final String RESOURCE_URL = "/statistics/";

    /**
     * Given none transaction when get then statistics count zero.
     */
    @Test
    public void givenNoneTransaction_whenGet_ThenStatisticsCountZero() {

        ResponseEntity<Statistics> response = client.exchange(RESOURCE_URL, HttpMethod.GET, HttpEntity.EMPTY, Statistics.class);

        assertNotNull(response.getBody());

        Statistics found = response.getBody();
        assertEquals(found.getCount(), (Long) 0L);
    }
}
