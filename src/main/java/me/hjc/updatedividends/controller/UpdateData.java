package me.hjc.updatedividends.controller;

import lombok.extern.slf4j.Slf4j;
import me.hjc.updatedividends.service.IDividendService;
import me.hjc.updatedividends.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class UpdateData implements CommandLineRunner {

    @Autowired
    IStockService stockService;

    @Autowired
    IDividendService dividendService;

    @Override
    public void run(String... args) throws IOException {
        Map<String, String> stocksMap;
        stocksMap = stockService.getStocksMap();
        if (Objects.requireNonNull(stocksMap).size() == 0) {
            log.error("获取股票列表数据异常");
            return;
        }
        stocksMap.forEach(this::upsertDividend);
    }

    private void upsertDividend(String key, String value) {
        try {
            dividendService.upsert(key, value);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
