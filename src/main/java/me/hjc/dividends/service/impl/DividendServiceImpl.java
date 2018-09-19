package me.hjc.dividends.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.hjc.dividends.config.MappingConfig;
import me.hjc.dividends.dao.IDividendDao;
import me.hjc.dividends.dao.ITradeDailyDao;
import me.hjc.dividends.model.Dividend;
import me.hjc.dividends.service.IDividendService;
import me.hjc.dividends.util.CountUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class DividendServiceImpl implements IDividendService {

    @Autowired
    private MappingConfig mappingConfig;

    @Autowired
    private IDividendDao dividendDao;
    @Autowired
    private ITradeDailyDao tradeDailyDao;

    void upsertDividends(String originalCode, String name) throws IOException, InterruptedException {
        String code = originalCode.substring(0, 6);
        String url = mappingConfig.getURL("dividendURL");
        Document doc;
        Elements elements = null;
        System.out.println("Processing stock: " + code + "...");
        rate();
        Thread.sleep(5000);
        try {
            doc = Jsoup.connect(url + code.substring(0, 6) + ".phtml").get();
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
            return;
        }
        int size = elements.size() / 9;
        int count = 0;
        try {
            count = dividendDao.countDividend(code);
        } catch (Exception e) {
            log.error("查询股票" + code + "的分红次数失败");
        }
        if (size > count || count == 0) {
//        if (true) {
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
                dividend.setRs(Double.valueOf(rs));
                dividend.setFs(Double.valueOf(fs));
                dividend.setIt(Double.valueOf(it));
                dividend.setSch(sch);
                dividend.setEdd(edd);
                dividend.setRd(rd);
                dividend.setRsod(rsod);
                Double dyr = 0d;
                if (!edd.equals("--")) {
                    dyr = this.calculateDyr(Double.valueOf(it), originalCode, edd);
                }
                dividend.setDyr(dyr);
                dividendDao.saveDividend(dividend);
            }
            log.info("更新了" + (size - count) + "条股票" + name + " " + code + "的分红数据");
        }
    }

    private void rate() {
        CountUtils.addCount();
        System.out.println("processing: " + CountUtils.getCount() + "/" + CountUtils.getTotal());
    }

    private double calculateDyr(double it, String code, String edd) {
        return Optional
                .ofNullable(tradeDailyDao.getCloseByDate(code, edd.replace("-", "")))
                .map(p -> ((int) ((it / (Double.valueOf(p) * 10)) * 10000 + 0.5)) / 10000.0).orElse(0d);
    }

    @Override
    @Async("executor")
    public void upsert(String code, String name) throws IOException, InterruptedException {
        upsertDividends(code, name);
    }
}
