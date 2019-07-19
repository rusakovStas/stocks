package com.stasdev.backend.services.impl;

import com.stasdev.backend.entitys.Stock;
import com.stasdev.backend.errors.NoInformationAboutLatestPrice;
import com.stasdev.backend.errors.NoInformationAboutSector;
import com.stasdev.backend.services.IEXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zankowski.iextrading4j.api.refdata.v1.ExchangeSymbol;
import pl.zankowski.iextrading4j.api.stocks.Company;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXCloudClient;
import pl.zankowski.iextrading4j.client.IEXCloudTokenBuilder;
import pl.zankowski.iextrading4j.client.IEXTradingApiVersion;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.refdata.v1.SymbolsRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.CompanyRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IEXServiceImpl implements IEXService {

    IEXCloudClient cloudClient = IEXTradingClient.create(IEXTradingApiVersion.IEX_CLOUD_V1,
            new IEXCloudTokenBuilder()
                    .withPublishableToken("pk_fe398d10d39f4c9b84055959b546fe15")
                    .withSecretToken("sk_0d9fb0c8d08549c7a13e7a652beaf35f")
                    .build());

    public IEXServiceImpl() {

    }

    @Override
    public List<Stock> getAllStocksFromIEX() {
        List<ExchangeSymbol> exchangeSymbols = cloudClient.executeRequest(new SymbolsRequestBuilder().build());
        return exchangeSymbols.stream()
                .map(this::mapExchangeSymbolsToStock)
                .collect(Collectors.toList());
    }

    @Override
    public Stock getPriceAndSectorForStock(Stock stock) {
        Company company = cloudClient.executeRequest(new CompanyRequestBuilder()
                .withSymbol(stock.getSymbol())
                .build());
        validateCompany(company, stock.getSymbol());
        return mapCompanyToStock(company, getPriceStock(stock));
    }

    @Override
    public Stock getPriceStock(Stock stock) {
        Quote quote = cloudClient.executeRequest(new QuoteRequestBuilder().withSymbol(stock.getSymbol()).build());
        validateQuote(quote, stock.getSymbol());
        return mapQuoteToStock(quote, stock);
    }

    private Stock mapExchangeSymbolsToStock(ExchangeSymbol exchangeSymbol) {
        return new Stock()
                .setName(exchangeSymbol.getName())
                .setSymbol(exchangeSymbol.getSymbol())
                .setIexId(exchangeSymbol.getIexId());
    }

    private void validateQuote(Quote quote, String symbol) {
        if (quote.getLatestPrice() == null) {
            throw new NoInformationAboutLatestPrice("No information about latest price for stock with symbol " + symbol);
        }
    }

    private void validateCompany(Company company, String symbol) {
        if (company.getSector() == null) {
            throw new NoInformationAboutSector("No information about sector for stock with symbol " + symbol);
        }
    }

    private Stock mapQuoteToStock(Quote quote, Stock stock) {
        return stock.setPrice(quote.getLatestPrice());
    }

    private Stock mapCompanyToStock(Company company, Stock stock) {
        return stock.setSector(company.getSector());
    }
}
