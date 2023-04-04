package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 19:11
 */
public interface IncomeTypeService {

    public JSONObject getIncomeTypeList();
    public JSONObject getIncomeTypeListByPage(int page, int limit);
    public JSONObject getIncomeTypeListByName(String name, int page, int limit);
    public JSONObject addIncomeType(String name);
    public JSONObject deleteIncomeByTypeById(int id);
    public JSONObject editIncomeType(int id, String name);
    public JSONObject getIncomeTypeNameById(int id);
}
