package com.stasdev.backend.entitys;

import java.math.BigDecimal;

public class Allocation {
    private String sector;
    private BigDecimal proportion;
    private BigDecimal assetValue;

    public String getSector() {
        return sector;
    }

    public Allocation setSector(String sector) {
        this.sector = sector;
        return this;
    }

    public BigDecimal getAssetValue() {
        return assetValue;
    }

    public Allocation setAssetValue(BigDecimal assetValue) {
        this.assetValue = assetValue;
        return this;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public Allocation setProportion(BigDecimal proportion) {
        this.proportion = proportion;
        return this;
    }

    @Override
    public String toString() {
        return "Allocation{" +
                "sector='" + sector + '\'' +
                ", assetValue=" + assetValue +
                ", proportion=" + proportion +
                '}';
    }
}
