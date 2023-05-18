package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.dao.ExpenseDao;
import com.nanyan.dao.IncomeDao;
import com.nanyan.dao.UserDebtDao;
import com.nanyan.entity.UserDebt;
import com.nanyan.service.ExpenseService;
import com.nanyan.service.IncomeService;
import com.nanyan.service.UserDebtService;
import com.nanyan.utils.OperationType;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDebtServiceImpl implements UserDebtService {

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Autowired
    UserDebtDao userDebtDao;

    @Autowired
    IncomeService incomeService;
    @Resource
    IncomeDao incomeDao;

    @Autowired
    ExpenseService expenseService;
    @Resource
    ExpenseDao expenseDao;

    @Override
    public JSONObject getDebtListByPage(int page, int limit) {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            //获取debt数量
            int debtNumber = userDebtDao.getDebtNumber();
            //获取debt列表
            List<UserDebt> debtListByPage = userDebtDao.getDebtListByPage(page, limit);

            Map<String,Object> tmpMap = new HashMap<>();
            for (int i = 0; i < debtListByPage.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(debtListByPage.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",debtNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getUserDebtListByPage(int page, int limit) {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            //获取debt数量
            int userDebtNumber = userDebtDao.getUserDebtNumber();
            //获取debt列表
            List<UserDebt> userDebtListByPage = userDebtDao.getUserDebtListByPage(page, limit);

            Map<String,Object> tmpMap = new HashMap<>();
            for (int i = 0; i < userDebtListByPage.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(userDebtListByPage.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",userDebtNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @OptLog(content = "添加债务", operationType = OperationType.INSERT)
    @Override
    public JSONObject addDebt(String debtName,int debtStatus, int debtType, Double debtPrice, String debtRemark, int debtOwnerId, String debtOwnerName) {

        HttpSession session = ServletActionContext.getRequest().getSession();

        UserDebt userDebt = new UserDebt();

        userDebt.setDebtName(debtName);
        userDebt.setDebtType(debtType);
        userDebt.setDebtPrice(debtPrice);
        userDebt.setDebtRemark(debtRemark);
        userDebt.setDebtOwnerId(debtOwnerId);
        userDebt.setDebtOwnerName(debtOwnerName);
        userDebt.setDebtCreateTime(new Timestamp(System.currentTimeMillis()));
        userDebt.setDebtStatus(debtStatus);

        //添加债务
        userDebtDao.addDebt(userDebt);

        // //添加到收支表
        // //1为借出，0为借入
        // if(debtType == 1) {
        //     expenseService.addExpense(debtOwnerName,7,new Timestamp(System.currentTimeMillis()),"借出债务", debtPrice);
        //     //添加完成后刷新数量
        //     session.setAttribute("expenseNumber",expenseDao.getExpenseNumber());
        //     //刷新支出总金额
        //     session.setAttribute("expenseCount",expenseDao.getExpenseCount());
        //
        // }else {
        //     incomeService.addIncome(debtOwnerName,5, new Timestamp(System.currentTimeMillis()),"借入债务", debtPrice);
        //     //添加完成后刷新数量
        //     session.setAttribute("incomeNumber",incomeDao.getIncomeNumber());
        //     //刷新总金额
        //     session.setAttribute("incomeCount",incomeDao.getIncomeCount());
        // }

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("code",1);
        dataMap.put("message","添加成功。");

        return new JSONObject(dataMap);
    }

    @OptLog(content = "删除债务", operationType = OperationType.UPDATE)
    @Override
    public JSONObject updateDeleted(int debtId) {

        userDebtDao.updateDeleted(debtId);

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("code",1);
        dataMap.put("message","删除成功。");

        return new JSONObject(dataMap);
    }

    @Override
    public JSONObject updateStatus(int debtId,int debtStatus) {

        userDebtDao.updateStatus(debtId,debtStatus);

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("code",1);
        dataMap.put("message","操作成功。");

        return new JSONObject(dataMap);
    }

    @OptLog(content = "更新负债", operationType = OperationType.UPDATE)
    @Override
    public JSONObject updateDebt(int debtId, String debtName, int debtType, Double debtPrice, String debtRemark, int debtOwnerId, String debtOwnerName,int debtStatus) {

        UserDebt userDebt = new UserDebt();
        userDebt.setDebtId(debtId);
        userDebt.setDebtName(debtName);
        userDebt.setDebtType(debtType);
        userDebt.setDebtPrice(debtPrice);
        userDebt.setDebtRemark(debtRemark);
        userDebt.setDebtOwnerId(debtOwnerId);
        userDebt.setDebtOwnerName(debtOwnerName);
        userDebt.setDebtStatus(debtStatus);

        userDebtDao.updateDebt(userDebt);

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("code",1);
        dataMap.put("message","修改成功。");

        return new JSONObject(dataMap);
    }

    @Override
    public JSONObject searchDebtList(int debtType,int page,int limit) {

        Map<String, Object> dataMap = new HashMap<>();
        try {
            //获取debt数量
            int userDebtNumber = userDebtDao.getSearchDebtNumber(debtType);
            //获取debt列表
            List<UserDebt> debtListByPage = userDebtDao.searchDebtList(debtType,page, limit);

            Map<String,Object> tmpMap = new HashMap<>();
            for (int i = 0; i < debtListByPage.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(debtListByPage.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",userDebtNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject searchUserDebtList(int debtType,int page,int limit) {

        Map<String, Object> dataMap = new HashMap<>();
        try {
            //获取debt数量
            int userDebtNumber = userDebtDao.getSearchUserDebtNumber(debtType);
            //获取debt列表
            List<UserDebt> userDebtListByPage = userDebtDao.searchUserDebtList(debtType,page, limit);

            Map<String,Object> tmpMap = new HashMap<>();
            for (int i = 0; i < userDebtListByPage.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(userDebtListByPage.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",userDebtNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }

    }
}
