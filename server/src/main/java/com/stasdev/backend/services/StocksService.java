package com.stasdev.backend.services;

import com.stasdev.backend.entitys.Stock;
import com.stasdev.backend.entitys.StockSummary;

import java.util.List;
import java.util.Set;

public interface StocksService {
    List<Stock> getAllStocks();
    List<Stock> getMyStocks(String userName);
    Stock addStockToFavorite(Stock stock, String userName);
    StockSummary getFavoriteStockSummary(String userName);
    void deleteStock(Long stockId, String currentUser);
}
