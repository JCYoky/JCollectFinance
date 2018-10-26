package me.hjc.finance.dao;

import me.hjc.finance.entity.TradeDailyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ITradeDailyDao {
    /**
     * 获取股票收盘价
     * @param code 股票代码 000001.SZ
     * @param date 交易日期 20180101
     * */
    @Select("select close from trade_daily where code = #{code} and trade_date = #{date}")
    String getCloseByDate(@Param("code") String code, @Param("date") String date);

    /**
     * 获取股票日K交易数据
     * @param code 股票代码 000001.SZ
     * */
    @Select("select trade_date, open, high, low, close from trade_daily where code = #{code} order by trade_date")
    List<TradeDailyEntity> getTradeList(@Param("code") String code);

    /**
     * 获取股票日k交易数据
     * @param code 股票代码 000001.SZ
     * @param startDate 起始日期 20100101
     * @param endDate 截止日期 20180101
     * */
    @Select("select trade_date, open, high, low, close " +
            "from trade_daily " +
            "where code = #{code} " +
            "and trade_date >= #{startDate}" +
            "and trade_date <= #{endDate}" +
            "order by trade_date")
    List<TradeDailyEntity> getTradeListPeriod(@Param("code") String code, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
