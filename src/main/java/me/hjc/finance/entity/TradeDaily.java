package me.hjc.finance.entity;

import lombok.Data;

@Data
public class TradeDaily {
    String tradeDate;
    String open;
    String high;
    String close;
}
