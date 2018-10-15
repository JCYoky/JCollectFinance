package me.hjc.finance.entity;

import lombok.Data;

/**
 * @Author: HJC
 * @Date: Created on 2018/7/15
 * 分红数据基本信息实体
 */
@Data
public class DividendEntity {
    int id;
    //股票名称
    String name;
    //股票代码
    String code;
    //公告日期
    String ad;
    //进度
    String sch;
    //分红对象
    String object;
    //派息股本基数
    String cb;
    //每10股现金(含税)
    Double it;
    //每10股送红股
    Double rs;
    //每10股转增股本
    Double fs;
    //股息率
    Double dyr;
    //股权登记日
    String rd;
    //除权除息日
    String edd;
    //股息到帐日
    String dad;
    //红股上市日
    String rsod;
    //转增股本上市日
    String fsod;
}
