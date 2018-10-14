package me.hjc.dividends.dao;

import me.hjc.dividends.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IStockDao {
    @Select("select * from stock_list")
    List<Stock> stocks();
}
