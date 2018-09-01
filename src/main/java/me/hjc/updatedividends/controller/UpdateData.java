package me.hjc.updatedividends.controller;

import lombok.extern.slf4j.Slf4j;
import me.hjc.updatedividends.config.MappingConfig;
import me.hjc.updatedividends.service.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class UpdateData implements CommandLineRunner {

    @Autowired
    MappingConfig mappingConfig;

    @Autowired
    CrawlService crawlService;

//    @Scheduled(cron = "0 0 3 * * ?")
    @Override
    public void run(String... args) throws Exception {
        long timeMillis = System.currentTimeMillis();
        String stocksURL = mappingConfig.getURL("stocksURL");
        String dividendURL = mappingConfig.getURL("dividendURL");
        Map<String, String> stocksMap;
        try {
             stocksMap = crawlService.getStocksMap(stocksURL);
        } catch (Exception e) {
            log.error("获取股票列表数据异常，重试中...：" + stocksURL);
            stocksMap = crawlService.getStocksMap(stocksURL);
        }
        if (stocksMap != null){
            stocksMap.forEach((key, value) -> {
                try {
                    crawlService.updateDividends(dividendURL, key, value);
                } catch (IOException e) {
                    log.error(new Date(System.currentTimeMillis()) + "IOException");
                } catch (InterruptedException e) {
                    log.error(new Date(System.currentTimeMillis()) + "InterruptedException");
                }
            });
            log.info(new Date(System.currentTimeMillis()) + "：更新股票分红数据成功！");
            System.out.println((System.currentTimeMillis() - timeMillis) / 1000);
        } else {
            log.error("获取股票列表数据异常..." + stocksURL);
        }
    }
}
