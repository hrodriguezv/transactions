/**
 * 
 */
package com.n26.transactions.service.impl;

import org.springframework.stereotype.Service;

import com.n26.transactions.model.Statistics;
import com.n26.transactions.model.Transaction;
import com.n26.transactions.repository.spec.ITransactionRepository;
import com.n26.transactions.service.spec.ITransactionService;

/**
 * Implementation of contract defined to allow the service management of transactions 
 * in real time.
 *
 * @author hrodriguez
 */
@Service
public class TransactionServiceImpl implements ITransactionService {

    /** The tx repository. */
    private ITransactionRepository txRepository;

    /**
     * Instantiates a new transaction service impl.
     *
     * @param transactionRepository the transaction repository
     */
    public TransactionServiceImpl(ITransactionRepository transactionRepository) {
        this.txRepository = transactionRepository;
    }

    /**
     * Save the transaction.
     *
     * @param t the t
     * @return the transaction
     */
    @Override
    public Transaction save(Transaction t) {
        return txRepository.save(t);
    }

    /**
     * Delete all transactions.
     */
    @Override
    public void deleteAll() {
        txRepository.deleteAllTransactions();
    }

    /**
     * Gets the statistics.
     *
     * @return the statistics
     */
    @Override
    public Statistics getStatistics() {
        return txRepository.calculateStatistics();
    }

}
