package com.nanyan.service;


import com.alibaba.fastjson.JSONObject;

public interface ExpenseService {
    
    public JSONObject getExpenseList();
    public JSONObject getExpenseListByPage(int page, int limit);
    public JSONObject getExpenseListByUserName(String userName,int page,int limit);
    public JSONObject addExpense(String userName,int expenseTypeId,double expenseAmount);
    public JSONObject deleteExpenseById(int id);
    public JSONObject editExpense(String username,int id,int expenseTypeId,double expenseAmount);


}
