package com.nanyan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public interface UserDebtService {
    public JSONObject getDebtListByPage(int page, int limit);
    public JSONObject getUserDebtListByPage(int page, int limit);
    public JSONObject addDebt(String debtName,int debtStatus, int debtType, Double debtPrice, String debtRemark, int debtOwnerId, String debtOwnerName);
    public JSONObject updateDeleted(int debtId);
    public JSONObject updateStatus(int debtId,int debtStatus);
    public JSONObject updateDebt(int debtId, String debtName, int debtType, Double debtPrice, String debtRemark, int debtOwnerId, String debtOwnerName,int debtStatus);

    public JSONObject searchDebtList(int debtType,int page,int limit);
    public JSONObject searchUserDebtList(int debtType,int page,int limit);
}
