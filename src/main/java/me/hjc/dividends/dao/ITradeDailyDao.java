package me.hjc.dividends.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ITradeDailyDao {

    @Select("select close from trade_daily where code = #{code} and trade_date = #{date}")
    String getCloseByDate(@Param("code") String code, @Param("date") String date);
}
