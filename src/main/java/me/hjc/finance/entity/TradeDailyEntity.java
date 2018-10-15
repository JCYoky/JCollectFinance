package me.hjc.finance.entity;

import lombok.Data;

/**
 * @author jc
 * @since 2018-10-13
 * 股票日线交易数据实体
 * */
@Data
public class TradeDailyEntity {
    /**
     * 交易日期
     * */
    String tradeDate;
    /**
     * 开盘价
     * */
    String open;
    /**
     * 最高价
     * */
    String high;
    /**
     * 最低价
     * */
    String low;
    /**
     * 收盘价
     * */
    String close;
}
