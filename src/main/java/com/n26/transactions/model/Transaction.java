package com.n26.transactions.model;

import java.math.BigDecimal;
import java.time.Instant;

import com.n26.transactions.validator.spec.InstantConstraint;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Reflects the values that should be stored in each transaction.
 *
 * @author hrodriguez
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {

    /** The amount. */
    private BigDecimal amount;

    /** The timestamp. */
    @InstantConstraint
    private Instant timestamp;
}
