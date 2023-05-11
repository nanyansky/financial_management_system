package com.nanyan.service;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;


public interface StockService {
    public JSONObject getStockList(String sort, String node, int pageSize, int pageIndex, int asc,String industryCode);
    public JSONObject getIndustryList();
    public JSONObject getUserStockListByPage(int page, int limit);
}
