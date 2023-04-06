package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;

public interface InitMenusService {
    public JSONObject returnMenus(int isAdmin);
}
