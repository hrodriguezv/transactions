/**
 * 
 */
package com.n26.transactions.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transactions.model.Statistics;
import com.n26.transactions.service.spec.ITransactionService;

/**
 * Exposes an endpoint to calculate real-time statistics for the 
 * last 60 seconds of transactions.
 *
 * @author hrodriguez
 */
@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController {

    /** The service. */
    private ITransactionService service;

    /**
     * Instantiates a new statistics controller.
     *
     * @param service the service
     */
    public StatisticsController(ITransactionService service) {
        super();
        this.service = service;
    }

    /**
     * Gets the statistics.
     *
     * @return the statistics
     */
    @GetMapping
    public Statistics getStatistics() {
        return service.getStatistics();
    }
}
