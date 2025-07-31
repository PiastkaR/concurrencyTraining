package org.multihread.metrics.non.atomic;

public class MinMaxMetrics {
    private volatile long minValue;
    private volatile long maxValue;

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        this.minValue = Long.MIN_VALUE;
        this.maxValue = Long.MAX_VALUE;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public void addSample(long newSample) {
        synchronized (this) {
            this.minValue = Math.min(newSample, this.minValue);
            this.maxValue = Math.max(newSample, this.maxValue);
        }
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        return this.minValue;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        return this.maxValue;
    }
}
