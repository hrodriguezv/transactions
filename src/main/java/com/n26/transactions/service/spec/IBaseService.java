package com.n26.transactions.service.spec;

import com.n26.transactions.exception.BaseException;

/**
 * The Interface IBaseService defines the basic operations 
 * to be exposed on service layer of the solution.
 *
 * @param <T> the generic type
 * @param <K> the key type
 * @author hrodriguez
 */
public interface IBaseService<T, K> {

    /**
     * Save.
     *
     * @param t the t
     * @return the t
     * @throws BaseException the base exception
     */
    T save(T t) throws BaseException;

    /**
     * Delete all.
     */
    void deleteAll();

    /**
     * Find all.
     *
     * @return the k
     */
    K getStatistics();

}
