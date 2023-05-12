package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.dao.ExpenseDao;
import com.nanyan.dao.UserDao;
import com.nanyan.entity.Expense;
import com.nanyan.entity.User;
import com.nanyan.service.ExpenseService;
import com.nanyan.utils.OperationType;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 11:50
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {



    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ExpenseDao expenseDao;
    @Autowired
    UserDao userDao;

    @Override
    public JSONObject getExpenseList() {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            //获取Expense数量
            int expenseNumber = expenseDao.getExpenseNumber();
            //获取Expense列表
            List<Expense> expenseList = expenseDao.getExpenseList();

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < expenseList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(expenseList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",expenseNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请`重试！");
            return new JSONObject(dataMap);
        }
    }


    @Override
    public JSONObject getExpenseListByPage(int page, int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            String IdKey = "expenseCacheIdSet";
            String EntityKey = "expenseCacheEntitySet";
            String NumKey = "expenseNum";
            int expenseNumber;
            List<Expense> expenseList;
            int start = (page-1)*limit + 1;
            int end = page*limit;
            //查找缓存
            String expenseNum = stringRedisTemplate.opsForValue().get(NumKey);
            Set<String> expenseIDSet = stringRedisTemplate.opsForZSet().rangeByScore(IdKey, start, end);


            Map<String,Object> tmpMap = new HashMap<>();
            if(expenseNum != null){
                int i = 0;
                expenseNumber = Integer.parseInt(expenseNum);
                for (String s : expenseIDSet) {
                    String o = (String) stringRedisTemplate.opsForHash().get(EntityKey, s);
                    tmpMap.put(String.valueOf(i++),JSONObject.parseObject(o));
                }
            }
            else {
                expenseNumber = expenseDao.getExpenseNumber();
                stringRedisTemplate.opsForValue().set(NumKey,String.valueOf(expenseNumber));

                expenseList = expenseDao.getExpenseList();
                int i =0;
                for (Expense expenseTmp : expenseList) {
                    tmpMap.put(String.valueOf(i++), JSON.toJSON(expenseTmp,serializeConfig));
                    stringRedisTemplate.opsForZSet().add(IdKey, String.valueOf(expenseTmp.getId()), i);
                    stringRedisTemplate.opsForHash().put(EntityKey,String.valueOf(expenseTmp.getId()),JSONObject.toJSONString(expenseTmp,serializeConfig));
                }
            }

//            //获取Expense数量
//            expenseNumber = expenseDao.getExpenseNumber();
//            //获取Expense列表
//            expenseList = expenseDao.getExpenseListByPage(page,limit);
//
//
//            for (int i = 0; i < expenseList.size(); i++) {
//                tmpMap.put(String.valueOf(i), JSON.toJSON(expenseList.get(i),serializeConfig));
//            }

            dataMap.put("code",0);
            dataMap.put("count",expenseNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getExpenseListByUserName(String userName, int page, int limit) {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            List<Expense> list = expenseDao.getExpenseListByUserName(userName,page,limit);

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < list.size(); i++) {
                tmpMap.put(String.valueOf(i),JSON.toJSON(list.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",list.size());
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject searchExpense(String username,String userNameAcc, int expenseTypeId, Timestamp startTime, Timestamp endTime, int page, int limit) {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            List<Expense> list = expenseDao.searchExpense(username,userNameAcc,expenseTypeId,startTime,endTime,page,limit);

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < list.size(); i++) {
                tmpMap.put(String.valueOf(i),JSON.toJSON(list.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",expenseDao.searchExpenseNumber(username,userNameAcc, expenseTypeId, startTime, endTime));
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject addExpense(String userName, int expenseTypeId,Timestamp expenseTime,String expenseContent, double expenseAmount) {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Expense expense = new Expense();
            //添加用户id
            expense.setUserId(userDao.findByUserName(userName).getId());
            expense.setUserName(userName);
            expense.setExpenseTypeId(expenseTypeId);
            expense.setExpenseTime(expenseTime);
            expense.setExpenseContent(expenseContent);
            expense.setExpenseAmount(expenseAmount);
            expense.setCreateTime(new Timestamp(System.currentTimeMillis()));

            //删除缓存
            stringRedisTemplate.delete("expenseCacheEntitySet");
            stringRedisTemplate.delete("expenseCacheIdSet");
            stringRedisTemplate.delete("expenseNum");

            expenseDao.addExpense(expense);
            //添加完成后刷新数量
            session.setAttribute("expenseNumber",expenseDao.getExpenseNumber());
            //刷新支出总金额
            session.setAttribute("expenseCount",expenseDao.getExpenseCount());

            dataMap.put("code",1);
            dataMap.put("message","添加成功！");
            return new JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "删除支出记录", operationType = OperationType.DELETE)
    public JSONObject deleteExpenseById(int id) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();

            //删除缓存
            stringRedisTemplate.delete("expenseCacheEntitySet");
            stringRedisTemplate.delete("expenseCacheIdSet");
            stringRedisTemplate.delete("expenseNum");

            expenseDao.deleteExpenseById(id);

            //删除后刷新数量
            session.setAttribute("expenseNumber",expenseDao.getExpenseNumber());
            //刷新支出总金额
            session.setAttribute("expenseCount",expenseDao.getExpenseCount());

            dataMap.put("code",1);
            dataMap.put("message","删除成功！");
            return new JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "编辑支出记录", operationType = OperationType.UPDATE)
    public JSONObject editExpense(String userName,int id, int expenseTypeId,Timestamp expenseTime,String expenseContent, double expenseAmount) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Expense expense = new Expense();
            //添加用户id
            expense.setUserId(userDao.findByUserName(userName).getId());
            expense.setUserName(userName);
            expense.setExpenseTypeId(expenseTypeId);
            expense.setExpenseTime(expenseTime);
            expense.setExpenseContent(expenseContent);
            expense.setExpenseAmount(expenseAmount);

            //删除缓存
            stringRedisTemplate.delete("expenseCacheEntitySet");
            stringRedisTemplate.delete("expenseCacheIdSet");
            stringRedisTemplate.delete("expenseNum");

            expenseDao.editExpense(id,expense);

            //刷新支出总金额
            session.setAttribute("expenseCount",expenseDao.getExpenseCount());

            dataMap.put("code",1);
            dataMap.put("message","修改成功！");
            return new JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }
}
