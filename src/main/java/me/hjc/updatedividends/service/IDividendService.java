package me.hjc.updatedividends.service;

import java.io.IOException;

public interface IDividendService {
    void upsert(String code, String name) throws IOException, InterruptedException;
}
