package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 11:48
 */

public interface IncomeService {
    public JSONObject getIncomeList();
    public JSONObject getIncomeListByPage(int page, int limit);
    public JSONObject getIncomeListByUserName(String userName,int page,int limit);
    public JSONObject addIncome(String userName,String incomeSource, double incomeAmount);
    public JSONObject deleteIncomeById(int id);
    public JSONObject editIncome(String userName,int id,String incomeSource,double incomeAmount,int isDeleted);

}
