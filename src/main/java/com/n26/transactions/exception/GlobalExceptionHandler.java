/**
 * 
 */
package com.n26.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * Class designed to determine the solution's behavior when encountering an execution error.
 *
 * @author hrodriguez
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle older transaction exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(OlderTXException.class)
    public ResponseEntity<Void> handleOlderTransactionException(OlderTXException ex) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handle future transaction exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(FutureTXException.class)
    public ResponseEntity<Void> handleFutureTransactionException(FutureTXException ex) {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handle invalid format exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Void> handleInvalidFormatException(InvalidFormatException ex) {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
