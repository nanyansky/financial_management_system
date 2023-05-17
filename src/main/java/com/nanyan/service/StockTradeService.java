package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;

public interface StockTradeService {

    public JSONObject save(String username, int userId, String stockCode, String stockName, double stockPrice, int stockNum, double tradePrice, Timestamp tradeTime, int tradeType);
    public JSONObject getTradeListByPage(int page, int limit );
    public JSONObject searchTradList(int page, int limit ,int tradType);
}
