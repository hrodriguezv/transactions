package com.n26.transactions.controller;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.n26.transactions.model.Transaction;

/**
 * The Class TransactionControllerIntegrationTest.
 * 
 * @author hrodriguez
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

    /** The client. */
    @Autowired
    private TestRestTemplate client;

    /** The Constant RESOURCE_URL. */
    private static final String RESOURCE_URL = "/transactions/";

    /**
     * Given new transaction when create then created.
     */
    @Test
    public void givenNewTransaction_whenCreate_ThenCreated() {

        Transaction tx = new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant());

        ResponseEntity<Void> response = client.postForEntity(RESOURCE_URL, tx, Void.class);

        assertTrue(response.getStatusCode() == HttpStatus.CREATED);
    }

    /**
     * Given new older transaction when create then no content.
     */
    @Test
    public void givenNewOlderTransaction_whenCreate_ThenNoContent() {

        Transaction tx = new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant()
            .minus(2, ChronoUnit.HOURS));

        ResponseEntity<Void> response = client.postForEntity(RESOURCE_URL, tx, Void.class);

        assertTrue(response.getStatusCode() == HttpStatus.NO_CONTENT);
    }

    /**
     * Given new future transaction when create then unprocessable entity.
     */
    @Test
    public void givenNewFutureTransaction_whenCreate_ThenUnprocessableEntity() {

        Transaction tx = new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant()
            .plus(1, ChronoUnit.HOURS));

        ResponseEntity<Void> response = client.postForEntity(RESOURCE_URL, tx, Void.class);

        assertTrue(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Given nothing when delete then no content.
     */
    @Test
    public void givenNothing_whenDelete_ThenNoContent() {

        ResponseEntity<Void> response = client.exchange(RESOURCE_URL, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        assertTrue(response.getStatusCode() == HttpStatus.NO_CONTENT);
    }
}
