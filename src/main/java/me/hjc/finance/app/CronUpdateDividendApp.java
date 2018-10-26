package me.hjc.finance.app;

import lombok.extern.slf4j.Slf4j;
import me.hjc.finance.entity.StockEntity;
import me.hjc.finance.service.IDividendService;
import me.hjc.finance.service.IStockService;
import me.hjc.finance.util.CountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class CronUpdateDividendApp {

    @Autowired
    IStockService stockService;

    @Autowired
    IDividendService dividendService;

    /**
     * 每月1日零点执行
     * */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void run() {
        log.info("开始更新股票分红数据...，执行时间：" + new Date(System.currentTimeMillis()));
        stockService.getStocks().map(stockEntities -> {
            CountUtils.setTotal(stockEntities.size());
            stockEntities.forEach(stockEntity -> this.upsertDividend(stockEntity.getCode(), stockEntity.getName()));
            return null;
        });
        log.info("更新分红数据成功");
    }

    private void upsertDividend(String code, String name) {
        try {
            dividendService.upsertDividends(code, name);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
