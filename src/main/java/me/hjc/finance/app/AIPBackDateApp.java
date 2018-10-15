package me.hjc.finance.app;

import me.hjc.finance.dao.IDividendDao;
import me.hjc.finance.dao.IStockDao;
import me.hjc.finance.dao.ITradeDailyDao;
import me.hjc.finance.entity.BackDateEntity;
import me.hjc.finance.entity.DividendEntity;
import me.hjc.finance.entity.StockEntity;
import me.hjc.finance.entity.TradeDailyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jc
 * @since 2018-10-13
 * 股票定投回溯计算：
 * 根据股票的日交易数据以及分红数据，计算从有交易数据起，每周定投至今的收益情况。
 * A.I.P
 * Automatic Investment Plan Back Date
 */
@Service
public class AIPBackDateApp {
    @Autowired
    IDividendDao dividendDao;
    @Autowired
    ITradeDailyDao tradeDailyDao;
    @Autowired
    IStockDao stockDao;

    /**
     * 股票定投回溯计算：
     * 根据股票的日交易数据以及分红数据，计算从有交易数据起，每周定投至今的收益情况。
     * A.I.P
     * Automatic Investment Plan Back Date
     */
    public void aipBackDate(String code) {
        StockEntity stockEntity = stockDao.getStock(code);
        List<TradeDailyEntity> tradeList = tradeDailyDao.getTradeList(code);
        List<DividendEntity> dividendEntities = dividendDao.getDividendByCode(code.substring(0, 6));
        System.out.println(this.calculation(tradeList, dividendEntities).toString());
    }

    /**
     * 股票定投回溯计算：
     * 根据股票的日交易数据以及分红数据，计算用户输入的起止期日间，每周定投收益情况。
     * A.I.P
     * Automatic Investment Plan Back Date
     */
    public void aipBackDate(String code, String startDate, String endDate) {
        List<TradeDailyEntity> tradeList = tradeDailyDao.getTradeList(code, startDate, endDate);
        List<DividendEntity> dividendEntities = dividendDao.getDividendByCode(code.substring(0, 6));
        System.out.println(this.calculation(tradeList, dividendEntities).toString());
    }

    private BackDateEntity calculation(List<TradeDailyEntity> tradeList, List<DividendEntity> dividendEntities) {
        List<String> eddList = dividendEntities.stream()
                .map(DividendEntity::getEdd)
                .map(edd -> edd.replace("-", ""))
                .collect(Collectors.toList());

        //投入的总金额（元人民币）
        int inputMoney = 0;
        //持有总份额（股）
        double totalShare = 0d;
        //总净值（元人民币）
        double netValue = 0;
        //定投期间的最大收益率（%）
        double maxProfitRate = 0;
        //获取最大收益率的日期
        String maxPRDate = "";
        //分红次数
        int dividendFlag = 0;
        //当日收益率
        double profitRateOfTheDay = 0;

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        //遍历日K交易数据
        for (int i = 0; i < tradeList.size(); i++) {
            TradeDailyEntity trade = tradeList.get(i);
            //分红情况的处理
            if (dividendFlag < eddList.size() && trade.getTradeDate().equals(eddList.get(dividendFlag))) {
                double dividend = ((totalShare / 10) * dividendEntities.get(dividendFlag).getIt());
                double eddOpenPrice = Double.valueOf(trade.getOpen());
                totalShare += (dividend / eddOpenPrice);
                dividendFlag++;
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
            //记录最大收益率
            if (profitRateOfTheDay > maxProfitRate) {
                maxProfitRate = profitRateOfTheDay;
                maxPRDate = trade.getTradeDate();
            }
        }
        BackDateEntity backDateEntity = new BackDateEntity();
        backDateEntity.setInput(Double.valueOf(decimalFormat.format(inputMoney)));
        backDateEntity.setNetValue(Double.valueOf(decimalFormat.format(netValue)));
        backDateEntity.setMaxProfitRate(Double.valueOf(decimalFormat.format(maxProfitRate)));
        backDateEntity.setMaxPRDate(maxPRDate);
        backDateEntity.setProfitRate(Double.valueOf(decimalFormat.format(profitRateOfTheDay)));
        return backDateEntity;
    }
}
