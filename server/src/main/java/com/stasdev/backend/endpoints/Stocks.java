package com.stasdev.backend.endpoints;

import com.stasdev.backend.entitys.Stock;
import com.stasdev.backend.entitys.StockSummary;
import com.stasdev.backend.services.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class Stocks {

    @Autowired
    private StocksService stocksService;

    @GetMapping
    List<Stock> getAllStocks(){
        return stocksService.getAllStocks();
    }

    @GetMapping("/my")
    List<Stock> getFavoriteStocks(Authentication authentication){
        return stocksService.getMyStocks(authentication.getName());
    }

    @GetMapping("/summary")
    StockSummary getFavoriteStockSummary(Authentication authentication){
        return stocksService.getFavoriteStockSummary(authentication.getName());
    }

    @PostMapping
    Stock addToFavorite(@RequestBody Stock stock, Authentication authentication){
        return stocksService.addStockToFavorite(stock, authentication.getName());
    }

    @DeleteMapping
    void deleteStockFromFavorite(@RequestParam("id") Long stockId, Authentication authentication){
        stocksService.deleteStock(stockId, authentication.getName());
    }

}
