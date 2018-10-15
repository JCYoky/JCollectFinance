package me.hjc.finance.entity;

import lombok.Data;

/**
 * @author jc
 * @since 2018-10-13
 * 股票定投回溯信息实体
 * */
@Data
public class BackDateEntity {
    String code;
    String name;
    String industry;
    /**
     * 投入金额
     * */
    Double input;
    /**
     * 截止日净值
     * */
    Double netValue;
    /**
     * 截止日收益率
     * */
    Double profitRate;
    /**
     * 定投期间最大收益率
     * */
    Double maxProfitRate;
    /**
     * 获取最大收益率的日期
     * */
    String maxPRDate;

    @Override
    public String toString() {
        return new String("投入金额：" + input + "\n" +
                "截止日期净资产总额：" + netValue + "\n" +
                "期间最大收益率：" + maxProfitRate + "\n" +
                "获取最大收益率的日期：" + maxPRDate + "\n" +
                "截止日收益率：" + profitRate);
    }
}
