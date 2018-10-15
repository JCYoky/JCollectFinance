package me.hjc.finance.controller;

import lombok.extern.slf4j.Slf4j;
import me.hjc.finance.entity.Stock;
import me.hjc.finance.service.IDividendService;
import me.hjc.finance.service.IStockService;
import me.hjc.finance.util.CountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class CronUpdateDividend {

    @Autowired
    IStockService stockService;

    @Autowired
    IDividendService dividendService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void run() {
        List<Stock> stocks = stockService.getStocks();
        CountUtils.setTotal(stocks.size());
        stocks.forEach(stock -> upsertDividend(stock.getCode(), stock.getName()));
        log.info("更新分红数据成功");
    }

    private void upsertDividend(String key, String value) {
        try {
            dividendService.upsert(key, value);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
