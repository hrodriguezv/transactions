package com.n26.transactions.validator.spec;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.n26.transactions.validator.impl.InstantConstraintValidator;

/**
 * Constraint annotation that can be used to ensure that the annotated field is valid, 
 * as long as it complies with business rule defined in its implementation.
 * 
 *  @author hrodriguez
 */
@Documented
@Constraint(validatedBy = InstantConstraintValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InstantConstraint {

    /**
     * Message.
     *
     * @return the string
     */
    String message() default "There was an error with this time instant reference";

    /**
     * Groups.
     *
     * @return the class[]
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     *
     * @return the class<? extends payload>[]
     */
    Class<? extends Payload>[] payload() default {};

}