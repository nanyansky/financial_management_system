package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.dao.UserAssetsDao;
import com.nanyan.entity.StockTrade;
import com.nanyan.entity.UserAssets;
import com.nanyan.service.UserAssetsService;
import com.nanyan.utils.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAssetsServiceImpl implements UserAssetsService {

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Autowired
    UserAssetsDao userAssetsDao;

    @Override
    public JSONObject getAssetsListByPage(int page, int limit) {

        Map<String, Object> dataMap = new HashMap<>();
        try {
            //获取assets数量
            int assetsNumber = userAssetsDao.getAssetsNumber();
            //获取assets列表
            List<UserAssets> assetsListByPage = userAssetsDao.getAssetsListByPage(page, limit);

            Map<String,Object> tmpMap = new HashMap<>();
            for (int i = 0; i < assetsListByPage.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(assetsListByPage.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",assetsNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getUserAssetsListByPage(int page, int limit) {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            //获取assets数量
            int userAssetsNumber = userAssetsDao.getUserAssetsNumber();
            //获取assets列表
            List<UserAssets> userAssetsListByPage = userAssetsDao.getUserAssetsListByPage(page, limit);

            Map<String,Object> tmpMap = new HashMap<>();
            for (int i = 0; i < userAssetsListByPage.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(userAssetsListByPage.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",userAssetsNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @OptLog(content = "添加资产", operationType = OperationType.INSERT)
    @Override
    public JSONObject addAssets(String assetsName, String assetsLocation, Double assetsPrice, String assetsRemark, int assetsOwnerId, String assetsOwnerName) {
        UserAssets userAssets = new UserAssets();

        userAssets.setAssetsName(assetsName);
        userAssets.setAssetsLocation(assetsLocation);
        userAssets.setAssetsPrice(assetsPrice);
        userAssets.setAssetsRemark(assetsRemark);
        userAssets.setAssetsOwnerId(assetsOwnerId);
        userAssets.setAssetsOwnerName(assetsOwnerName);
        userAssets.setAssetsCreateTime(new Timestamp(System.currentTimeMillis()));

        userAssetsDao.addAssets(userAssets);

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("code",1);
        dataMap.put("message","添加成功。");

        return new JSONObject(dataMap);
    }

    @OptLog(content = "删除资产", operationType = OperationType.UPDATE)
    @Override
    public JSONObject updateDeleted(int assetsId) {

        System.out.println(assetsId);
        userAssetsDao.updateDeleted(assetsId);

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("code",1);
        dataMap.put("message","删除成功。");

        return new JSONObject(dataMap);
    }

    @OptLog(content = "更新资产", operationType = OperationType.UPDATE)
    @Override
    public JSONObject updateAssets(int assetsId, String assetsName, String assetsLocation, Double assetsPrice, String assetsRemark, int assetsOwnerId, String assetsOwnerName) {
        UserAssets userAssets = new UserAssets();

        userAssets.setAssetsId(assetsId);
        userAssets.setAssetsName(assetsName);
        userAssets.setAssetsLocation(assetsLocation);
        userAssets.setAssetsPrice(assetsPrice);
        userAssets.setAssetsRemark(assetsRemark);
        userAssets.setAssetsOwnerId(assetsOwnerId);
        userAssets.setAssetsOwnerName(assetsOwnerName);

        userAssetsDao.updateAssets(userAssets);

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("code",1);
        dataMap.put("message","修改成功。");

        return new JSONObject(dataMap);
    }
}
