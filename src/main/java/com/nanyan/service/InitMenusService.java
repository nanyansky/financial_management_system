package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public interface InitMenusService {
    public JSONObject returnMenus(int isAdmin) throws IOException;
}
