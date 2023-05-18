package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.dao.OperationLogDao;
import com.nanyan.entity.OperationLog;
import com.nanyan.service.OperationLogService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/1 11:45
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {


    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    int page;
    int limit;

    @Resource
    OperationLogDao operationLogDao;

    @Override
    public void addOperationLog(OperationLog operationLog) {
        operationLogDao.addOperationLog(operationLog);
    }

    @Override
    public JSONObject getLogList() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            int logNumber = operationLogDao.getLogNumber();
            List<OperationLog> logList = operationLogDao.getLogList();
            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < logList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(logList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",logNumber);
            dataMap.put("data",tmpMap);
            return new JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getLogListByPage(int page,int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            int logNumber = operationLogDao.getLogNumber();
            List<OperationLog> logList = operationLogDao.getLogListByPage(page,limit);

//            System.out.println(logList);
            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < logList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(logList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",logNumber);
            dataMap.put("data",tmpMap);
//            System.out.println(dataMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getLogListByUserName(String username, int page, int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<OperationLog> logList = operationLogDao.getLogListByUserName(username,page,limit);

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < logList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(logList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",logList.size());
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject searchOperationLog(String username, Timestamp startTime, Timestamp endTime, int page, int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<OperationLog> logList = operationLogDao.searchOperationLog(username,startTime,endTime,page,limit);

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < logList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(logList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",operationLogDao.searchOperationLogNumber(username, startTime, endTime));
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getTop10OperationLog() {
        List<OperationLog> top10OperationLog = operationLogDao.getTop10OperationLog();

        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("top10OperationLog",top10OperationLog);
        return null;
    }
}
