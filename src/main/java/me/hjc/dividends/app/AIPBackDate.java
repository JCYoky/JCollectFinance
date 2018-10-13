package me.hjc.dividends.app;

import me.hjc.dividends.dao.IDividendDao;
import me.hjc.dividends.dao.ITradeDailyDao;
import me.hjc.dividends.entity.Dividend;
import me.hjc.dividends.entity.TradeDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class AIPBackDate {

    /**
     * 投入的总金额（元人民币）
     */
    private int inputMoney = 0;
    /**
     * 持有总份额（股）
     */
    private double totalShare = 0d;
    /**
     * 总净值（元人民币）
     */
    private double netValue = 0;
    /**
     * 定投期间的最大收益率（%）
     */
    private double maxProfitRate = 0;

    @Autowired
    IDividendDao dividendDao;
    @Autowired
    ITradeDailyDao tradeDailyDao;

    /**
     * 股票定投回溯计算：
     * 根据股票的日交易数据以及分红数据，计算从有交易数据起，每周定投至今的收益情况。
     * A.I.P
     * Automatic Investment Plan Back Date
     */
    public void aipBackDate(String code) {
        String number = code.substring(0, 6);
        List<TradeDaily> tradeList = tradeDailyDao.getTradeByCode(code);
        List<Dividend> dividends = dividendDao.getDividendByCode(number);

        List<String> eddList = new ArrayList<>();
        dividends.forEach(dividend -> eddList.add(dividend.getEdd()));//获取除权除息日列表
        int dividendFlag = 0;
        double profitRateOfTheDay = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        //遍历日K交易数据
        for (int i = 0; i < tradeList.size(); i++) {
            TradeDaily trade = tradeList.get(i);
            //分红情况的处理
            if (trade.getTradeDate().equals(dividends.get(dividendFlag).getEdd().replace("-", ""))) {
                double dividend = ((totalShare / 10) * dividends.get(dividendFlag).getIt());
                double eddOpenPrice = Double.valueOf(trade.getOpen());
                totalShare += (dividend / eddOpenPrice);
                if (dividendFlag + 1 < dividends.size()) {
                    dividendFlag++;
                }
            }
            //每5个交易日买入一手
            if (i % 5 == 0) {
                //买入一手
                double openOfTheDay = Double.valueOf(tradeList.get(i).getOpen());
                inputMoney += (int) openOfTheDay * 100;
                totalShare += 100d;
            }
            //当日净值
            netValue = totalShare * Double.valueOf(trade.getClose());
            //当日收益率
            profitRateOfTheDay = (netValue - inputMoney) / inputMoney;
            //计算最大收益率
            maxProfitRate = profitRateOfTheDay > maxProfitRate ? profitRateOfTheDay : maxProfitRate;
        }
        System.out.println("投入的金钱总额：" + inputMoney + " 元RMB");
        System.out.println("今日的净资产总额：" + decimalFormat.format(netValue) + " 元RMB");
        System.out.println("期间的最大收益率为：" + decimalFormat.format(maxProfitRate * 100) + "%");
        System.out.println("收益率：" + decimalFormat.format(profitRateOfTheDay * 100) + "%");
    }
}
