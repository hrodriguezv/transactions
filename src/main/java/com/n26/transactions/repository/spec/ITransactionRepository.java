/**
 * 
 */
package com.n26.transactions.repository.spec;

import com.n26.transactions.exception.BaseException;
import com.n26.transactions.exception.FutureTXException;
import com.n26.transactions.exception.OlderTXException;
import com.n26.transactions.model.Statistics;
import com.n26.transactions.model.Transaction;
import com.n26.transactions.util.N26Constants;

/**
 * Defines the operations that should be supported to store transactions.
 * The implementation of this contract belongs to the persistence layer of this solution.
 *  
 * @author hrodriguez
 */
public interface ITransactionRepository {

    /**
     * Save the transaction.
     *
     * @param tx the tx
     * @return the transaction
     * @throws BaseException the base exception
     */
    Transaction save(Transaction tx) throws BaseException;

    /**
     * Calculate statistics.
     *
     * @return the statistics
     */
    Statistics calculateStatistics();

    /**
     * Delete all transactions.
     */
    void deleteAllTransactions();

    /**
     * Manage base exception.
     *
     * @param message the message
     */
    default void manageBaseException(String message) {

        switch (message) {
        case N26Constants.INVALID_FUTURE_TX_CODE:
            throw new FutureTXException();
        case N26Constants.INVALID_OLD_TX_CODE:
            throw new OlderTXException();
        default:
            throw new BaseException();
        }
    }
}
