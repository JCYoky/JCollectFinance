package me.hjc.finance.service;

import me.hjc.finance.entity.DividendEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author jc
 * @since 2018-09-20
 * 股票分红数据服务接口
 * */
public interface IDividendService {
    /**
     * 更新股票分红数据
     * */
    void upsertDividends(String code, String name) throws IOException, InterruptedException;

    /**
     * 获取股票分红数据列表
     * */
    Optional<List<DividendEntity>> getDividendByCode(String code);
}
