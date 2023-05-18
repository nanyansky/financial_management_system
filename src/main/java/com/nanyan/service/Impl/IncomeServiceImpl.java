package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.dao.IncomeDao;
import com.nanyan.dao.UserDao;
import com.nanyan.entity.Income;
import com.nanyan.entity.User;
import com.nanyan.service.IncomeService;
import com.nanyan.utils.OperationType;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 11:48
 */
@Service
public class IncomeServiceImpl implements IncomeService {


    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    IncomeDao incomeDao;
    @Autowired
    UserDao userDao;
    
    @Override
    public JSONObject getIncomeList() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //获取Income数量
            int incomeNumber = incomeDao.getIncomeNumber();
            //获取income列表
            List<Income> incomeList = incomeDao.getIncomeList();

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < incomeList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(incomeList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",incomeNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    public JSONObject getIncomeListByPage(int page, int limit) {
        Map<String, Object> dataMap = new HashMap<>();
        try {

            String IdKey = "incomeCacheIdSet";
            String EntityKey = "incomeCacheEntitySet";
            String NumKey = "incomeNum";
            int incomeNumber;
            List<Income> incomeList;
            int start = (page-1)*limit + 1;
            int end = page*limit;
            //查找缓存
            String incomeNum = stringRedisTemplate.opsForValue().get(NumKey);
            Set<String> incomeIDSet = stringRedisTemplate.opsForZSet().rangeByScore(IdKey, start, end);

//            System.out.println(incomeIDSet);

            Map<String,Object> tmpMap = new HashMap<>();
            if(incomeNum != null){
                int i = 0;
                incomeNumber = Integer.parseInt(incomeNum);
                for (String s : incomeIDSet) {
                    String o = (String) stringRedisTemplate.opsForHash().get(EntityKey, s);
                    tmpMap.put(String.valueOf(i++),JSONObject.parseObject(o));
                }
            }
            else {
                incomeNumber = incomeDao.getIncomeNumber();
                stringRedisTemplate.opsForValue().set(NumKey,String.valueOf(incomeNumber));

                incomeList = incomeDao.getIncomeList();
                int i =0;
                for (Income incomeTmp : incomeList) {
                    tmpMap.put(String.valueOf(i++), JSON.toJSON(incomeTmp,serializeConfig));
                    stringRedisTemplate.opsForZSet().add(IdKey, String.valueOf(incomeTmp.getId()), i);
                    stringRedisTemplate.opsForHash().put(EntityKey,String.valueOf(incomeTmp.getId()),JSONObject.toJSONString(incomeTmp,serializeConfig));
                }
            }

//            //获取Income数量
//            incomeNumber = incomeDao.getIncomeNumber();
//            //获取income列表
//            incomeList = incomeDao.getIncomeListByPage(page,limit);
//
//            for (int i = 0; i < incomeList.size(); i++) {
//                tmpMap.put(String.valueOf(i), JSON.toJSON(incomeList.get(i),serializeConfig));
//            }

            dataMap.put("code",0);
            dataMap.put("count",incomeNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getIncomeListByUserName(String userName, int page, int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<Income> list = incomeDao.getIncomeListByUserName(userName,page,limit);

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
    @OptLog(content = "搜索收入账单", operationType = OperationType.SELECT)
    public JSONObject searchIncome(String userName,String userNameAcc, int incomeTypeId, Timestamp startTime, Timestamp endTime,int page, int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<Income> list = incomeDao.searchIncome(userName,userNameAcc,incomeTypeId,startTime,endTime,page,limit);

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < list.size(); i++) {
                tmpMap.put(String.valueOf(i),JSON.toJSON(list.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",incomeDao.searchIncomeNumber(userName,userNameAcc,incomeTypeId,startTime,endTime));
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "添加收入记录", operationType = OperationType.INSERT)
    public JSONObject addIncome(String userName,int incomeTypeId, Timestamp incomeTime, String incomeContent, double incomeAmount) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Income income = new Income();
            //添加用户id
            income.setUserId(userDao.findByUserName(userName).getId());
            income.setUserName(userName);
            income.setIncomeTypeId(incomeTypeId);
            income.setIncomeTime(incomeTime);
            income.setIncomeContent(incomeContent);
            income.setIncomeAmount(incomeAmount);
            income.setCreateTime(new Timestamp(System.currentTimeMillis()));

            //删除缓存
            stringRedisTemplate.delete("incomeCacheEntitySet");
            stringRedisTemplate.delete("incomeCacheIdSet");
            stringRedisTemplate.delete("incomeNum");

            incomeDao.addIncome(income);
            //添加完成后刷新数量
            session.setAttribute("incomeNumber",incomeDao.getIncomeNumber());
            //刷新总金额
            session.setAttribute("incomeCount",incomeDao.getIncomeCount());

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
    @OptLog(content = "删除收入记录", operationType = OperationType.DELETE)
    public JSONObject deleteIncomeById(int id) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();

            //删除缓存
            stringRedisTemplate.delete("incomeCacheEntitySet");
            stringRedisTemplate.delete("incomeCacheIdSet");
            stringRedisTemplate.delete("incomeNum");

            incomeDao.deleteIncomeById(id);

            //删除后刷新数量
            session.setAttribute("incomeNumber",incomeDao.getIncomeNumber());
            //刷新总金额
            session.setAttribute("incomeCount",incomeDao.getIncomeCount());

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
    @OptLog(content = "编辑收入记录", operationType = OperationType.UPDATE)
    public JSONObject editIncome(String userName, int id, int incomeTypeId, Timestamp incomeTime, String incomeContent, double incomeAmount, int isDeleted) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Income income = new Income();
            //添加用户id
            income.setUserId(userDao.findByUserName(userName).getId());
            income.setUserName(userName);
            income.setIncomeTypeId(incomeTypeId);
            income.setIncomeTime(incomeTime);
            income.setIncomeContent(incomeContent);
            income.setIncomeAmount(incomeAmount);
            income.setIsDeleted(isDeleted);

            //删除缓存
            stringRedisTemplate.delete("incomeCacheEntitySet");
            stringRedisTemplate.delete("incomeCacheIdSet");
            stringRedisTemplate.delete("incomeNum");

            incomeDao.editIncome(id,income);
            //刷新总金额
            session.setAttribute("incomeCount",incomeDao.getIncomeCount());

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
