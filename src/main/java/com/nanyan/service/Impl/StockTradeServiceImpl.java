package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.dao.StockTradeDao;
import com.nanyan.entity.StockTrade;
import com.nanyan.service.StockTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockTradeServiceImpl implements StockTradeService {

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Autowired
    StockTradeDao stockTradeDao;


    @Override
    public JSONObject save(String username, int userId, String stockCode, String stockName, double stockPrice, int stockNum, double tradePrice, Timestamp tradeTime, int tradeType) {

        StockTrade stockTrade = new StockTrade();
        stockTrade.setUsername(username);
        stockTrade.setUserId(userId);
        stockTrade.setStockCode(stockCode);
        stockTrade.setStockName(stockName);
        stockTrade.setStockPrice(stockPrice);
        stockTrade.setStockNum(stockNum);
        stockTrade.setTradePrice(tradePrice);
        stockTrade.setTradeTime(tradeTime);
        stockTrade.setTradeType(tradeType);

        stockTradeDao.save(stockTrade);
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("code",1);
        dataMap.put("message","添加成功。");

        return new JSONObject(dataMap);
    }

    @Override
    public JSONObject getTradeListByPage(int page, int limit ) {

        Map<String, Object> dataMap = new HashMap<>();
        try {
            //获取StockTrade数量
            int tradeNumber = stockTradeDao.getTradeNumber();
            //获取StockTrade列表
            List<StockTrade> TradeListByPage = stockTradeDao.getTradeListByPage(page, limit);

            Map<String,Object> tmpMap = new HashMap<>();
            for (int i = 0; i < TradeListByPage.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(TradeListByPage.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",tradeNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject searchTradeList(int page, int limit,int tradeType) {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            //获取StockTrade数量
            int tradeNumber = stockTradeDao.getSearchTradeNumber(tradeType);
            //获取StockTrade列表
            List<StockTrade> TradeListByPage = stockTradeDao.searchTradList(page, limit,tradeType);

            Map<String,Object> tmpMap = new HashMap<>();
            for (int i = 0; i < TradeListByPage.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(TradeListByPage.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",tradeNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }
}
