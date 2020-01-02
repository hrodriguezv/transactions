/**
 * 
 */
package com.n26.transactions.validator.impl;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.n26.transactions.util.N26Constants;
import com.n26.transactions.validator.spec.InstantConstraint;

/**
 * Defines the logic to validate a given constraint on Instant references used by a given object type
 * used on the scope of this solution.
 * 
 *  @author hrodriguez
 */
public class InstantConstraintValidator implements ConstraintValidator<InstantConstraint, Instant> {

    /**
     * Checks if is valid.
     *
     * @param instantReference the instant reference
     * @param context the context
     * @return true, if is valid
     */
    @Override
    public boolean isValid(Instant instantReference, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        Instant rigthNow = Clock.systemUTC()
            .instant();

        if (instantReference.isAfter(rigthNow)) {
            context.buildConstraintViolationWithTemplate(N26Constants.INVALID_FUTURE_TX_CODE)
                .addConstraintViolation();
            return false;
        }

        long difference = Duration.between(instantReference, rigthNow)
            .getSeconds();

        if (difference >= 60) {
            context.buildConstraintViolationWithTemplate(N26Constants.INVALID_OLD_TX_CODE)
                .addConstraintViolation();
            return false;
        }

        return true;
    }

}
