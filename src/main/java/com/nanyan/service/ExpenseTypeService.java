package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.entity.ExpenseType;


import java.util.List;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 19:11
 */
public interface ExpenseTypeService {

    public JSONObject getExpenseTypeList();
    public JSONObject getExpenseTypeListByPage(int page, int limit);
    public JSONObject getExpenseTypeListByName(String name, int page, int limit);
    public JSONObject addExpenseType(String name);
    public JSONObject deleteExpenseByTypeById(int id);
    public JSONObject editExpenseType(int id, String name);
    public JSONObject getExpenseTypeNameById(int id);
}
