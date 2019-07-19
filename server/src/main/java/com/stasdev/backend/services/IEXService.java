package com.stasdev.backend.services;

import com.stasdev.backend.entitys.Stock;

import java.util.List;

public interface IEXService {

    List<Stock> getAllStocksFromIEX();
    Stock getPriceAndSectorForStock(Stock stock);
    Stock getPriceStock(Stock stock);
}
