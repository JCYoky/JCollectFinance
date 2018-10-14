package me.hjc.dividends.entity;

import lombok.Data;

/**
 * 股票代码实体
 * */
@Data
public class Stock {
    String code;
    String symbol;
    String name;
    String area;
    String industry;
    String market;
    String listDate;
}
