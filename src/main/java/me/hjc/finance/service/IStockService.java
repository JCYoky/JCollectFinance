package me.hjc.finance.service;

import me.hjc.finance.entity.Stock;

import java.util.List;

public interface IStockService {
    List<Stock> getStocks();
}
