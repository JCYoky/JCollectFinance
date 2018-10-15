package me.hjc.finance.dao;

import me.hjc.finance.entity.StockEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IStockDao {
    @Select("select * from stock_list")
    List<StockEntity> stocks();

    @Select("select * from stock_list where code = #{code}")
    StockEntity getStock(@Param("code") String code);
}
