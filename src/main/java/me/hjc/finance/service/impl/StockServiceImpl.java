package me.hjc.finance.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.hjc.finance.config.MappingConfig;
import me.hjc.finance.dao.IStockDao;
import me.hjc.finance.entity.StockEntity;
import me.hjc.finance.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StockServiceImpl implements IStockService {

    @Autowired
    MappingConfig mappingConfig;

    @Autowired
    IStockDao stockDao;

    @Override
    public List<StockEntity> getStocks() {
        return stockDao.stocks();
    }
}
