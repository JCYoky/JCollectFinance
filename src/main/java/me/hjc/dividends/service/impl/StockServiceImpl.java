package me.hjc.dividends.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.hjc.dividends.config.MappingConfig;
import me.hjc.dividends.dao.IStockDao;
import me.hjc.dividends.model.StockCode;
import me.hjc.dividends.service.IStockService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StockServiceImpl implements IStockService {

    @Autowired
    MappingConfig mappingConfig;

    @Autowired
    IStockDao stockDao;

    @Override
    public Map<String, String> getStocksMap() throws IOException {
        Document document;
        String url = mappingConfig.getURL("stocksURL");
        Map<String, String> stocks = new HashMap<>();
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            document = Jsoup.connect(url).get();
        }
        if (document != null) {
            Elements elements = document.getElementsByClass("qox").select("a[href]");
            elements.forEach(element -> {
                String innerText = element.text();
                String[] codeAndName = innerText.split("\\(");
                if (codeAndName.length == 2) {
                    String code = codeAndName[1].substring(0, 6);
                    if (code.startsWith("600") || code.startsWith("601")//沪市A股
                            || code.startsWith("300")//创业板
                            || code.startsWith("000") || code.startsWith("001")//深市A股
                            || code.startsWith("002")) {//中小板
                        stocks.put(code, codeAndName[0]);
                    }
                }
            });
        }
        return stocks;
    }

    @Override
    public List<StockCode> getStocks() {
        return stockDao.stocks();
    }
}
