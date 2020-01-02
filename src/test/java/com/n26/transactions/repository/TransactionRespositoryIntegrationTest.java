/**
 * 
 */
package com.n26.transactions.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.n26.transactions.exception.FutureTXException;
import com.n26.transactions.exception.OlderTXException;
import com.n26.transactions.model.Statistics;
import com.n26.transactions.model.Transaction;
import com.n26.transactions.repository.spec.ITransactionRepository;

/**
 * The Class TransactionRespositoryIntegrationTest.
 *
 * @author hrodriguez
 */
@SpringBootTest
public class TransactionRespositoryIntegrationTest {

    /** The repository. */
    @Autowired
    private ITransactionRepository repository;

    /**
     * Given new transaction when create then OK.
     */
    @Test
    public void givenNewTransaction_whenCreate_thenOK() {
        repository.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant()));
    }

    /**
     * Given new older transaction when create then fail.
     */
    @Test
    public void givenNewOlderTransaction_whenCreate_thenFail() {

        Assertions.assertThrows(OlderTXException.class, () -> {
            repository.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
                .instant()
                .minus(2, ChronoUnit.HOURS)));
        });
    }

    /**
     * Given new future transaction when create then fail.
     */
    @Test
    public void givenNewFutureTransaction_whenCreate_thenFail() {

        Assertions.assertThrows(FutureTXException.class, () -> {
            repository.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
                .instant()
                .plus(1, ChronoUnit.HOURS)));
        });
    }

    @Test
    public void givenThreeCreatedTx_whenCalculateStatistics_thenOK() {
        repository.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant()));
        repository.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant()));
        repository.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant()));
        
        Statistics statistics = repository.calculateStatistics();
        assertEquals(statistics.getSum(), new BigDecimal("30"));
    }

    @Test
    public void givenThreeCreatedTx_whenDeleteAllAndCalculateStatistics_thenOK() {
        repository.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant()));
        repository.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant()));
        repository.save(new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant()));

        repository.deleteAllTransactions();
        
        Statistics statistics = repository.calculateStatistics();
        assertEquals(statistics.getSum(), new BigDecimal("0"));
    }

}
