package com.n26.transactions.model;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.n26.transactions.validator.spec.InstantConstraint;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Reflects the statistics or KPI's based on the transactions that happened 
 * in the last 60 seconds.
 * 
 * @author hrodriguez
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Statistics {

    /** The sum. */
    private BigDecimal sum;

    /** The avg. */
    private BigDecimal avg;

    /** The max. */
    private BigDecimal max;

    /** The min. */
    private BigDecimal min;

    /** The count. */
    private Long count;

    /** The last reference. */
    @JsonIgnore
    @InstantConstraint
    private Instant lastReference;

    /**
     * Aggregate the content of transaction.
     *
     * @param tx the tx
     * @return the statistics
     */
    public Statistics aggregate(Transaction tx) {
        final BigDecimal sum = this.sum.add(tx.getAmount());
        final BigDecimal max = this.max.equals(BigDecimal.ZERO) ? tx.getAmount() : this.max.max(tx.getAmount());
        final BigDecimal min = this.min.equals(BigDecimal.ZERO) ? tx.getAmount() : this.min.min(tx.getAmount());
        final Long count = this.count + 1;
        final BigDecimal avg = sum.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
        return new Statistics(sum, avg, max, min, count, tx.getTimestamp());
    }

    /**
     * Accumulate the value of each KPI.
     *
     * @param aggregatedStatistics the aggregated statistics
     * @return the statistics
     */
    public Statistics aggregate(Statistics aggregatedStatistics) {
        final BigDecimal sum = this.sum.add(aggregatedStatistics.getSum());
        final BigDecimal max = this.max.equals(BigDecimal.ZERO) ? aggregatedStatistics.getMax() : this.max.max(aggregatedStatistics.getMax());
        final BigDecimal min = this.min.equals(BigDecimal.ZERO) ? aggregatedStatistics.getMin() : this.min.min(aggregatedStatistics.getMin());
        final Long count = this.count + aggregatedStatistics.getCount();
        final BigDecimal avg = count == 0 ? BigDecimal.ZERO : sum.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
        return new Statistics(sum, avg, max, min, count, aggregatedStatistics.lastReference);
    }

    /**
     * Initialize the data structure.
     *
     * @return the statistics
     */
    public static Statistics initialize() {
        return new Statistics(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0L, Clock.systemUTC()
            .instant());
    }
}
