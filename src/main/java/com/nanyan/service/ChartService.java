package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;

public interface ChartService {
    public JSONObject getIncomeCount();
    public JSONObject getExpenseCount();
}
