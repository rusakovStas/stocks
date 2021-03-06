package com.stasdev.backend.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    private String iexId;
    private String symbol;
    private String sector;
    private BigDecimal price;
//  TODO в теории данное поле лучше вынести в отдельную таблицу
    private BigDecimal volume;
    private String name;

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", iexId='" + iexId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                ", name='" + name + '\'' +
                '}';
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    private ApplicationUser user;

    public Long getStockId() {
        return stockId;
    }

    public Stock setStockId(Long stockId) {
        this.stockId = stockId;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public Stock setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public Stock setVolume(BigDecimal volume) {
        this.volume = volume;
        return this;
    }

    public String getName() {
        return name;
    }

    public Stock setName(String name) {
        this.name = name;
        return this;
    }

    public String getIexId() {
        return iexId;
    }

    public Stock setIexId(String iexId) {
        this.iexId = iexId;
        return this;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public Stock setUser(ApplicationUser user) {
        this.user = user;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Stock setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getSector() {
        return sector;
    }

    public Stock setSector(String sector) {
        this.sector = sector;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock stock = (Stock) o;
        return getSymbol().equals(stock.getSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbol());
    }
}
