/**
 * 
 */
package com.n26.transactions.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.n26.transactions.model.Transaction;

/**
 * The Class InstantConstraintValidatorTest.
 * 
 * @author hrodriguez
 */
public class InstantConstraintValidatorTest {

    /** The validator. */
    private Validator validator;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Given new transaction when validate against right now then success.
     */
    @Test
    public void givenNewTransaction_whenValidateAgainstRightNow_thenSuccess() {
        Transaction tx = new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant());
        Set<ConstraintViolation<Transaction>> violations = validator.validate(tx);
        assertTrue(violations.isEmpty());
    }
    
    /**
     * Given new transaction when validate against passed time then FAIL.
     */
    @Test
    public void givenNewTransaction_WhenValidateAgainstPassedTime_thenFAIL() {
        Transaction tx = new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant().minus(2, ChronoUnit.HOURS));
        Set<ConstraintViolation<Transaction>> violations = validator.validate(tx);
        assertFalse(violations.isEmpty());
    }

    /**
     * Given new transaction when validate against future time then FAIL.
     */
    @Test
    public void givenNewTransaction_whenValidateAgainstFutureTime_thenFAIL() {
        Transaction tx = new Transaction(BigDecimal.TEN, Clock.systemUTC()
            .instant().plus(1, ChronoUnit.HOURS));
        Set<ConstraintViolation<Transaction>> violations = validator.validate(tx);
        assertFalse(violations.isEmpty());
    }
}
