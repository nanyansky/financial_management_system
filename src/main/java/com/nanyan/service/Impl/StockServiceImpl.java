package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.StockService;
import com.nanyan.utils.StockDate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {
    @Override
    public JSONObject getStockList(String sort, String node, int pageSize, int pageIndex, int asc, String industryCode) {

        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("sort",sort);
        requestBody.put("node",node);
        requestBody.put("pageSize",pageSize);
        requestBody.put("pageIndex",pageIndex);
        requestBody.put("asc",asc);
        requestBody.put("industryCode",industryCode);

        return StockDate.stockRank(requestBody);
    }

    @Override
    public JSONObject getIndustryList() {

        return null;
    }

    @Override
    public JSONObject getUserStockListByPage(int page, int limit ) {

        return null;
    }
}
