package com.stasdev.backend.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    private String iexId;
    private String symbol;
    private String sector;
    private BigDecimal price;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "stocks", fetch = FetchType.EAGER)
    private List<ApplicationUser> users = new ArrayList<>();

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

    public List<ApplicationUser> getUsers() {
        return users;
    }

    public Stock setUsers(List<ApplicationUser> users) {
        this.users = users;
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
}
