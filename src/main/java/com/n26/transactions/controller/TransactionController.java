/**
 * 
 */
package com.n26.transactions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transactions.model.Transaction;
import com.n26.transactions.service.spec.ITransactionService;

/**
 * Exposes an endpoint to receive real-time transactions that will be persisted 
 * in a built-in memory store.
 *
 * @author hrodriguez
 */
@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    /** The service. */
    ITransactionService service;

    /**
     * Instantiates a new transaction controller.
     *
     * @param service the service
     */
    public TransactionController(ITransactionService service) {
        super();
        this.service = service;
    }

    /**
     * Receives the transactions.
     *
     * @param tx the tx
     * @return 
     */
    @PostMapping
    public ResponseEntity<Void> receiveTransaction(@RequestBody Transaction tx) {

        if (tx == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        service.save(tx);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    /**
     * Reset data store.
     * 
     * @return 
     */
    @DeleteMapping
    public ResponseEntity<Void> resetData() {
        service.deleteAll();
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
