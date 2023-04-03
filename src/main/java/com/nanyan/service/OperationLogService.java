package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.entity.OperationLog;

public interface OperationLogService {
    public void addOperationLog(OperationLog operationLog);

    public JSONObject getLogList();
    public JSONObject getLogListByPage(int page,int limit);

    public JSONObject getLogListByUserName(String username,int page,int limit);
}
