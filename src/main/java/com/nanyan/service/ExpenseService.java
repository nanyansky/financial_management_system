package com.nanyan.service;


import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;

public interface ExpenseService {
    
    public JSONObject getExpenseList();
    public JSONObject getExpenseListByPage(int page, int limit);
    public JSONObject getExpenseListByUserName(String userName,int page,int limit);
    public JSONObject searchExpense(String userName,String userNameAcc, int expenseTypeId, Timestamp startTime, Timestamp endTime, int page, int limit);
    public JSONObject addExpense(String userName, int expenseTypeId, Timestamp expenseTime, String expenseContent, double expenseAmount);
    public JSONObject deleteExpenseById(int id);
    public JSONObject editExpense(String username,int id,int expenseTypeId,Timestamp expenseTime,String expenseContent,double expenseAmount);


}
