package com.nanyan.service;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;


public interface StockService {
    public JSONObject getStockList(String sort, String node, int pageSize, int pageIndex, int asc,String industryCode);
    public JSONObject getUserStockListByPage(int page, int limit);
    public JSONObject buyStock(String code,String name,String price,int stockNum);
    public JSONObject sellStock(int stockId,String code,String name,String price,String curPrice,int stockNum,int sellNum);

    public JSONObject getStockIndustry();

}
