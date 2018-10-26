package me.hjc.finance.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.hjc.finance.config.MappingConfig;
import me.hjc.finance.dao.IStockDao;
import me.hjc.finance.entity.StockEntity;
import me.hjc.finance.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StockServiceImpl implements IStockService {

    @Autowired
    MappingConfig mappingConfig;

    @Autowired
    IStockDao stockDao;

    @Override
    public Optional<List<StockEntity>> getStocks() {
        return Optional.ofNullable(stockDao.stocks());
    }

    @Override
    public Optional<StockEntity> getStock(String code) {
        return Optional.ofNullable(stockDao.getStock(code));
    }
}
