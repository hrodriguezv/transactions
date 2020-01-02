/**
 * 
 */
package com.n26.transactions.repository.impl;

import static com.n26.transactions.util.N26Constants.NUM_MAX_TX_ALLOWED;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Repository;

import com.n26.transactions.model.Statistics;
import com.n26.transactions.model.Transaction;
import com.n26.transactions.repository.spec.ITransactionRepository;

/**
 * Implementation of contract defined to allow the persistence management of transactions 
 * in real time.
 * 
 * @author hrodriguez
 */
@Repository
public class BuiltInMemoryTransactionRepoImpl implements ITransactionRepository {

    /** The base memory. */
    private Statistics[] baseMemory = new Statistics[NUM_MAX_TX_ALLOWED];

    /** The data store. */
    private List<Statistics> dataStore;

    /** The constraint validator. */
    private Validator constraintValidator;

    /**
     * Instantiates a new built in memory repo impl.
     *
     * @param validator the validator
     */
    public BuiltInMemoryTransactionRepoImpl(Validator validator) {
        init();
        this.constraintValidator = validator;
    }

    /**
     * Initialize the data store with defaults.
     */
    private void init() {
        Arrays.fill(baseMemory, Statistics.initialize());
        this.dataStore = Collections.synchronizedList(Arrays.asList(baseMemory));
    }

    /**
     * Save the transaction.
     *
     * @param tx the tx
     * @return the transaction
     */
    @Override
    public Transaction save(Transaction tx) {

        Set<ConstraintViolation<Transaction>> violations = constraintValidator.validate(tx);

        if (!violations.isEmpty())
            manageBaseException(violations.iterator()
                .next()
                .getMessage());

        int slot = LocalDateTime.ofInstant(tx.getTimestamp(), ZoneOffset.UTC)
            .getSecond();

        synchronized (this.dataStore) {
            this.dataStore.set(slot, dataStore.get(slot)
                .aggregate(tx));
        }

        return tx;
    }

    /**
     * Calculate statistics.
     *
     * @return the statistics
     */
    @Override
    public Statistics calculateStatistics() {

        Statistics reduced = this.dataStore.stream()
            .filter(statistic -> constraintValidator.validate(statistic)
                .isEmpty())
            .filter(statistic -> statistic.getCount() != 0)
            .reduce(Statistics.initialize(), Statistics::aggregate, (statA, statB) -> statA);

        return reduced;
    }

    /**
     * Delete all transactions.
     */
    @Override
    public void deleteAllTransactions() {
        synchronized (this.dataStore) {
            init();
        }
    }

}
