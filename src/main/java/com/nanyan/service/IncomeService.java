package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;

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
    public JSONObject searchIncome(String userName,String userNameAcc, int incomeTypeId,Timestamp startTime, Timestamp endTime,int page, int limit);
    public JSONObject addIncome(String userName, int incomeTypeId, Timestamp incomeTime, String incomeContent, double incomeAmount);
    public JSONObject deleteIncomeById(int id);
    public JSONObject editIncome(String userName,int id,int incomeTypeId, Timestamp incomeTime, String incomeContent,double incomeAmount,int isDeleted);

}
