package me.hjc.dividends.dao;

import me.hjc.dividends.entity.TradeDaily;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ITradeDailyDao {

    @Select("select close from trade_daily where code = #{code} and trade_date = #{date}")
    String getCloseByDate(@Param("code") String code, @Param("date") String date);

    @Select("select trade_date, open, high, close from trade_daily where code = #{code} order by trade_date")
    List<TradeDaily> getTradeByCode(@Param("code") String code);
}
