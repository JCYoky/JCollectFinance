package me.hjc.finance.service;

import me.hjc.finance.entity.StockEntity;

import java.util.List;

public interface IStockService {
    List<StockEntity> getStocks();
}
