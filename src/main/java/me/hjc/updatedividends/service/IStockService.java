package me.hjc.updatedividends.service;

import java.io.IOException;
import java.util.Map;

public interface IStockService {
    Map<String, String> getStocksMap() throws IOException;
}
