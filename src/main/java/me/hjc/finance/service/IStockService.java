package me.hjc.finance.service;

import me.hjc.finance.entity.StockEntity;

import java.util.List;
import java.util.Optional;

public interface IStockService {
    Optional<List<StockEntity>> getStocks();
    Optional<StockEntity> getStock(String code);
}
