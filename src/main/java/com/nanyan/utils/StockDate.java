package com.nanyan.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StockDate {
    //获取股票列表
    public static JSONObject stockRank(Map<String, Object> requestBody) {

        String url = "https://api.doctorxiong.club/v1/stock/rank";
        String originDataString = "";
        try {
            originDataString  = HttpUtil.createPost(url).contentType("application/json").body(JSON.toJSONString(requestBody)).execute().body();
        } catch (Exception e) {
            throw new RuntimeException("第三方接口异常");
        }

        HashMap<String, Object> dataMap = new HashMap<>();

        JSONObject originData = JSON.parseObject(originDataString);
        //股票数据列表以及其他参数
        String data = originData.getString("data");
        // 股票总数
        JSONObject tmplData = JSON.parseObject(data);
        String count = tmplData.getString("totalRecord");
        //只有股票数据
        String rank = JSON.parseObject(data).getString("rank");

        HashMap<String, Object> tmpMap = new HashMap<>();
        //剔除不需要的数据
        JSONArray jsonArray = JSONArray.parseArray(rank);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            jsonObject.remove("minData");
            jsonObject.remove("buy");
            jsonObject.remove("sell");
            tmpMap.put(String.valueOf(i), jsonObject);
        }

        dataMap.put("code", 0);
        dataMap.put("count", count);
        dataMap.put("data", tmpMap);

        return new JSONObject(dataMap);
    }

    // //获取行业板块列表
    // public static JSONObject industryList() {
    //
    //     String url = "https://api.doctorxiong.club/v1/stock/industry/rank";
    //     String originDataString = "";
    //     try {
    //         originDataString  = HttpUtil.createPost(url).contentType("application/json").body(JSON.toJSONString(requestBody)).execute().body();
    //     } catch (Exception e) {
    //         throw new RuntimeException("第三方接口异常");
    //     }
    //
    //     HashMap<String, Object> dataMap = new HashMap<>();
    //     JSONObject originData = JSON.parseObject(originDataString);
    //     //行业板块信息
    //     String data = originData.getString("data");
    //     JSONArray jsonArray = JSONArray.parseArray(data);
    //
    //     HashMap<String, Object> tmpMap = new HashMap<>();
    //     dataMap.put("count", jsonArray.size());
    //     for (int i = 0; i < jsonArray.size(); i++) {
    //         JSONObject jsonObject = jsonArray.getJSONObject(i);
    //         tmpMap.put(String.valueOf(i), jsonObject);
    //     }
    //
    //     return null;
    // }


}
