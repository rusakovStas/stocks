package com.stasdev.backend.services.impl;

import com.stasdev.backend.entitys.Allocation;
import com.stasdev.backend.entitys.ApplicationUser;
import com.stasdev.backend.entitys.Stock;
import com.stasdev.backend.entitys.StockSummary;
import com.stasdev.backend.errors.InvalidStock;
import com.stasdev.backend.errors.StockIsAlreadyInFavorite;
import com.stasdev.backend.errors.StockNotFound;
import com.stasdev.backend.errors.UserNotFound;
import com.stasdev.backend.repos.ApplicationUserRepository;
import com.stasdev.backend.repos.StocksRepository;
import com.stasdev.backend.services.IEXService;
import com.stasdev.backend.services.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StocksServiceImpl implements StocksService {

    private final StocksRepository stocksRepository;
    private final IEXService iexService;
    private final ApplicationUserRepository userRepository;

    @Autowired
    public StocksServiceImpl(StocksRepository stocksRepository, IEXService iexService, ApplicationUserRepository userRepository) {
        this.stocksRepository = stocksRepository;
        this.iexService = iexService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Stock> getAllStocks() {
        return iexService.getAllStocksFromIEX();
    }

    @Override
    public List<Stock> getMyStocks(String userName) {
        ApplicationUser currentUser = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFound("User with name " + userName + " not found"));
        return stocksRepository.findAllByUser(currentUser)
                .parallelStream()
                .map(iexService::getPriceStock)//Обновляем котировки при запросе избранных акций
                .peek(stocksRepository::saveAndFlush).sorted(Comparator.comparing(Stock::getStockId)).collect(Collectors.toList());
    }

    @Override
    public Stock addStockToFavorite(Stock stock, String userName) {
        ApplicationUser currentUser = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFound("User with name " + userName + " not found"));
        this.validateStock(stock);
        if (currentUser.getStocks().contains(stock)) {
            throw new StockIsAlreadyInFavorite("Stock with symbol '" + stock.getSymbol() + "' is already in your favorite list");
        }
        Stock stockWithPriceAndSector = iexService.getPriceAndSectorForStock(stock);
        stockWithPriceAndSector.setUser(currentUser);
        currentUser.getStocks().add(stockWithPriceAndSector);
        return stocksRepository.saveAndFlush(stockWithPriceAndSector);
    }

    @Override
    public StockSummary getFavoriteStockSummary(String userName) {
        ApplicationUser currentUser = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFound("User with name " + userName + " not found"));
        List<Stock> myStocks = stocksRepository.findAllByUser(currentUser);
        return summary(myStocks);
    }

    @Override
    public void deleteStock(Long stockId, String userName) {
        Stock byId = stocksRepository.findById(stockId).orElseThrow(() -> new StockNotFound("stock with id " + stockId + " not found"));
        ApplicationUser currentUser = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFound("User with name " + userName + " not found"));
        if (!currentUser.getStocks().contains(byId)) {
            throw new RuntimeException("You don't have that stocks in your favorite list");
        }
        currentUser.getStocks().remove(byId);
        userRepository.saveAndFlush(currentUser);
        stocksRepository.deleteById(stockId);
        stocksRepository.flush();
    }

    private StockSummary summary(List<Stock> stocks) {
        List<Allocation> allocations = new ArrayList<>();
        Map<String, BigDecimal> sectorsSumm = new HashMap<>();
        BigDecimal value = new BigDecimal("0");
        for (Stock stock : stocks) {
            sectorsSumm.computeIfPresent(stock.getSector(), (k, v) -> v.add(stock.getVolume().multiply(stock.getPrice())));
            sectorsSumm.computeIfAbsent(stock.getSector(), (k) -> stock.getVolume().multiply(stock.getPrice()));
            value = value.add(stock.getPrice().multiply(stock.getVolume()));
        }
        for (Map.Entry<String, BigDecimal> sectorSumm : sectorsSumm.entrySet()) {
            Allocation allocation = new Allocation();
            allocation.setSector(sectorSumm.getKey());
            allocation.setAssetValue(sectorSumm.getValue());
            allocation.setProportion(sectorSumm.getValue().divide(value, 3, RoundingMode.HALF_UP));
            allocations.add(allocation);
        }
        return new StockSummary()
                .setAllocations(allocations)
                .setValue(value);
    }

    private void validateStock(Stock stock) {
        if (stock.getSymbol() == null || stock.getSymbol().isEmpty()) {
            throw new InvalidStock("Symbol can't be null");
        }
        if (stock.getVolume() == null) {
            throw new InvalidStock("Volume can't be null");
        }
    }
}
