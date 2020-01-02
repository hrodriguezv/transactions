/**
 * 
 */
package com.n26.transactions.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.n26.transactions.exception.FutureTXException;
import com.n26.transactions.exception.OlderTXException;
import com.n26.transactions.model.Transaction;
import com.n26.transactions.service.spec.ITransactionService;

/**
 * The Class TransactionServiceIntegrationTest.
 *
 * @author hrodriguez
 */
@SpringBootTest
public class TransactionServiceIntegrationTest {

    /** The service. */
    @Autowired
    private ITransactionService service;

    /**
     * Given new invalid old transaction when create then fail.
     */
    @Test
    public void givenNewInvalidOldTransaction_whenCreate_thenFail() {

        Assertions.assertThrows(OlderTXException.class, () -> {
            service.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
                .instant()
                .minus(2, ChronoUnit.HOURS)));
        });
    }

    /**
     * Given new invalid future transaction when create then fail.
     */
    @Test
    public void givenNewInvalidFutureTransaction_whenCreate_thenFail() {

        Assertions.assertThrows(FutureTXException.class, () -> {
            service.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
                .instant()
                .plus(1, ChronoUnit.HOURS)));
        });
    }

}
