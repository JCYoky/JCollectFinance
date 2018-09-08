package me.hjc.dividends.service;

import me.hjc.dividends.model.StockCode;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IStockService {
    Map<String, String> getStocksMap() throws IOException;
    List<StockCode> getStocks();
}
