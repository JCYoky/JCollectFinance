package me.hjc.finance.service;

import me.hjc.finance.entity.Dividend;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IDividendService {
    void upsert(String code, String name) throws IOException, InterruptedException;
    Optional<List<Dividend>> getDividendByCode(String code);
}
