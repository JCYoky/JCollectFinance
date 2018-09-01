package me.hjc.updatedividends.service;

import lombok.extern.slf4j.Slf4j;
import me.hjc.updatedividends.dao.IDividendDao;
import me.hjc.updatedividends.model.Dividend;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CrawlService {

    @Autowired
    private IDividendDao dividendDao;

    public Map<String, String> stocks = new HashMap<>();

    public Map<String, String> getStocksMap(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("qox").select("a[href]");
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
        return stocks;
    }

    @Async("executor")
    public void updateDividends(String url, String code, String name) throws IOException, InterruptedException {
        Document doc;
        Elements elements = null;
        System.out.println("Processing stock: " + code + "...");
        Thread.sleep(5000);
        try {
            doc = Jsoup.connect(url + code + ".phtml").get();
            elements = doc.getElementsByAttributeValue("id", "sharebonus_1").select("td");
        } catch (Exception e) {
            System.out.println("请求股票：" + code + " 异常！将进行重试...");
            for (int i = 1; i <= 10; i++) {
                Thread.sleep(5000);
                System.out.println("正在进行对股票：" + code + " 第" + i + "次重试...");
                doc = Jsoup.connect(url + code + ".phtml").get();
                elements = doc.getElementsByAttributeValue("id", "sharebonus_1").select("td");
                if (elements.size() > 0 && elements.size() % 9 == 0) {
                    System.out.println(code + "重试获取成功！");
                    break;
                }
            }
        }
        if (elements.size() == 0 || elements.size() % 9 != 0) {
            System.out.println("股票：" + code + " 数据错误！");
        }
        int size = elements.size() / 9;
        int count = 0;
        try {
            count = dividendDao.countDividend(code);
        } catch (Exception e) {
            log.error("查询股票" + code + "的分红次数失败");
        }
        if (size > count || count == 0) {
            dividendDao.deleteDividendByCode(code);
            Dividend dividend;
            for (int i = 0; i < size; i++) {
                String ad = elements.get(i * 9).text();
                String rs = elements.get(i * 9 + 1).text();
                String fs = elements.get(i * 9 + 2).text();
                String it = elements.get(i * 9 + 3).text();
                String sch = elements.get(i * 9 + 4).text();
                String edd = elements.get(i * 9 + 5).text();
                String rd = elements.get(i * 9 + 6).text();
                String rsod = elements.get(i * 9 + 7).text();
                dividend = new Dividend();
                dividend.setCode(code);
                dividend.setName(name);
                dividend.setAd(ad);
                dividend.setRs(rs);
                dividend.setFs(fs);
                dividend.setIt(it);
                dividend.setSch(sch);
                dividend.setEdd(edd);
                dividend.setRd(rd);
                dividend.setRsod(rsod);
                dividendDao.saveDividend(dividend);
            }
            log.info("更新了" + (size - count) + "条股票" + name + " " + code + "的分红数据");
        } else {
//            log.info("不需更新" + code);
        }
    }
}
