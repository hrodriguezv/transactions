package com.n26.transactions.service.spec;

import com.n26.transactions.model.Statistics;
import com.n26.transactions.model.Transaction;

/**
 * Defines the operations to consult statistics of transactions  of the last 60 seconds.
 * The implementation of this contract belongs to the service layer of this solution.
 * 
 * @author hrodriguez
 */
public interface ITransactionService extends IBaseService<Transaction, Statistics> {

    /**
     * Gets the statistics.
     *
     * @return the statistics
     */
    Statistics getStatistics();

}
