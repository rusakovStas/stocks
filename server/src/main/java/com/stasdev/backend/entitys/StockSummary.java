package com.stasdev.backend.entitys;

import java.math.BigDecimal;
import java.util.List;

public class StockSummary {

    private BigDecimal value;
    private List<Allocation> allocations;

    public BigDecimal getValue() {
        return value;
    }

    public StockSummary setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public List<Allocation> getAllocations() {
        return allocations;
    }

    public StockSummary setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
        return this;
    }

    @Override
    public String toString() {
        return "StockSummary{" +
                "value=" + value +
                ", allocations=" + allocations +
                '}';
    }
}
