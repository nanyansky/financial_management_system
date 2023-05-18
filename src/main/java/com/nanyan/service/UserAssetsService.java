package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

public interface UserAssetsService {
    public JSONObject getAssetsListByPage(int page, int limit);
    public JSONObject getUserAssetsListByPage(int page, int limit);
    public JSONObject addAssets(String assetsName, String assetsLocation, Double assetsPrice, String assetsRemark, int assetsOwnerId, String assetsOwnerName);
    public JSONObject updateDeleted(int assetsId);
    public JSONObject updateAssets(int assetsId, String assetsName, String assetsLocation, Double assetsPrice, String assetsRemark, int assetsOwnerId, String assetsOwnerName);
}
