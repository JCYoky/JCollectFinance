package me.hjc.finance.dao;

import me.hjc.finance.entity.DividendEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hjc
 *
 * 分红数据实体的持久层
 * */
@Mapper
@Component
public interface IDividendDao {

    /**
     * 插入分红数据
     * */
    @Insert("insert into dividend (name, code, ad, sch, rs, fs, dyr, it, edd, rd, rsod)" +
            "values" +
            "(#{name}, #{code}, #{ad}, #{sch}, #{rs}, #{fs}, #{dyr}, #{it}, #{edd}, #{rd}, #{rsod})")
    void saveDividend(DividendEntity dividendEntity);

    /**
     * 根据股票代码获取分红记录条数
     * */
    @Select("select count(1) from dividend where code = #{code}")
    int countDividend(@Param("code") String code);

    /**
     * 根据股票代码获取股票的分红历史数据
     * */
    @Select("select * from dividend where code = #{code} order by ad")
    List<DividendEntity> getDividendByCode(@Param("code") String code);

    /**
     * 根据股票代码删除股票分红记录
     * */
    @Delete("delete from dividend where code = #{code}")
    void deleteDividendByCode(@Param("code") String code);
}
