/**
 * 
 */
package com.n26.transactions.util;

/**
 * The N26Constants allows to exposes fields constants that will be used 
 * for different classes on this solution.
 *
 * @author hrodriguez
 */
public final class N26Constants {

    /**
     * Instantiates a new n 26 constants.
     */
    private N26Constants() {
    }

    /** The Constant NUM_MAX_TX_ALLOWED. */
    public static final int NUM_MAX_TX_ALLOWED = 60;

    /** The Constant INVALID_OLD_TX_CODE. */
    public static final String INVALID_OLD_TX_CODE = "OLD-TX";
    
    /** The Constant INVALID_FUTURE_TX_CODE. */
    public static final String INVALID_FUTURE_TX_CODE = "FUTURE-TX";
    
}
