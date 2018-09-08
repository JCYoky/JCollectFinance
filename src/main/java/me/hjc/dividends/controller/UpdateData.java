package me.hjc.dividends.controller;

import lombok.extern.slf4j.Slf4j;
import me.hjc.dividends.model.StockCode;
import me.hjc.dividends.service.IDividendService;
import me.hjc.dividends.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class UpdateData implements CommandLineRunner {

    @Autowired
    IStockService stockService;

    @Autowired
    IDividendService dividendService;

    @Override
    public void run(String... args) {
        List<StockCode> stocks = stockService.getStocks();
        stocks.forEach(stockCode -> upsertDividend(stockCode.getCode(), stockCode.getName()));
    }

    private void upsertDividend(String key, String value) {
        try {
            dividendService.upsert(key, value);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
