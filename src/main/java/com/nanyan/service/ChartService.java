package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;

public interface ChartService {
    public JSONObject getIncomeCount(String username);
    public JSONObject getExpenseCount(String username);
    public JSONObject getIncomeTypeData(String username);
    public JSONObject getExpenseTypeData(String username);
}
