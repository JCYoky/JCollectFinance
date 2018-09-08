package me.hjc.dividends.dao;

import me.hjc.dividends.model.Dividend;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface IDividendDao {

    @Insert("insert into dividend (name, code, ad, sch, rs, fs, it, edd, rd, rsod)" +
            "values" +
            "(#{name}, #{code}, #{ad}, #{sch}, #{rs}, #{fs}, #{it}, #{edd}, #{rd}, #{rsod})")
    void saveDividend(Dividend dividend);

    @Select("select count(1) from dividend where code = #{code}")
    int countDividend(@Param("code") String code);

    @Delete("delete from dividend where code = #{code}")
    void deleteDividendByCode(@Param("code") String code);
}
