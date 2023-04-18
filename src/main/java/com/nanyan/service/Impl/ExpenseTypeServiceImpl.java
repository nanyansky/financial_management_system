package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.dao.ExpenseTypeDao;
import com.nanyan.entity.Expense;
import com.nanyan.entity.ExpenseType;
import com.nanyan.service.ExpenseTypeService;
import com.nanyan.utils.OperationType;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
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
 * @date 2023/4/2 19:11
 */
@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Autowired
    ExpenseTypeDao expenseTypeDao;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public JSONObject getExpenseTypeList() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //获取Expense数量
            int expenseTypeNumber = expenseTypeDao.getExpenseTypeNumber();
            //获取Expense列表
            List<ExpenseType> expenseTypeList = expenseTypeDao.getExpenseTypeList();

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < expenseTypeList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(expenseTypeList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",expenseTypeNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getExpenseTypeListByPage(int page, int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //获取Expense数量
            int expenseTypeNumber = expenseTypeDao.getExpenseTypeNumber();
            //获取Expense列表
            List<ExpenseType> expenseTypeList = expenseTypeDao.getExpenseTypeListByPage(page,limit);

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < expenseTypeList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(expenseTypeList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",expenseTypeNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getExpenseTypeListByName(String name, int page, int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<ExpenseType> list = expenseTypeDao.getExpenseTypeListByName(name,page,limit);

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
    @OptLog(content = "添加支出分类", operationType = OperationType.INSERT)
    public JSONObject addExpenseType(String name) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            ExpenseType tmpExpenseType = expenseTypeDao.getExpenseTypeByName(name);


            if (tmpExpenseType == null) {
                ExpenseType expenseType = new ExpenseType();

                expenseType.setName(name);
                expenseType.setCreateTime(new Timestamp(System.currentTimeMillis()));

                expenseTypeDao.addExpenseType(expenseType);

                dataMap.put("code",1);
                dataMap.put("message","添加成功！");
                return new JSONObject(dataMap);
            } else {
                dataMap.put("code",0);
                dataMap.put("message","分类名称已存在！");
                return new JSONObject(dataMap);
            }
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "删除支出分类", operationType = OperationType.DELETE)
    public JSONObject deleteExpenseByTypeById(int id) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            expenseTypeDao.deleteExpenseByTypeById(id);

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
    @OptLog(content = "编辑支出分类", operationType = OperationType.UPDATE)
    public JSONObject editExpenseType(int id, String name) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            ExpenseType tmpExpenseType = expenseTypeDao.getExpenseTypeByName(name);


            if (tmpExpenseType == null || tmpExpenseType.getId() == id) {
                ExpenseType expenseType = new ExpenseType();
                //添加用户id
                expenseType.setName(name);

                expenseTypeDao.editExpenseType(id,expenseType);

                dataMap.put("code",1);
                dataMap.put("message","修改成功！");
                return new JSONObject(dataMap);
            } else {
                dataMap.put("code",0);
                dataMap.put("message","分类名称已存在！");
                return new JSONObject(dataMap);
            }
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getExpenseTypeNameById(int id) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //Redis中的key
        String key = "expenseTypeId:"+id;
        //1.从缓存中取分类
        String name = stringRedisTemplate.opsForValue().get(key);
        //2.判断是否存在
        if (name != null) {
            //3.存在，直接返回
            dataMap.put("name",name);
            return new JSONObject(dataMap);
        }

        //4.不存在，查询数据库，并写入缓存
        String expenseTypeNameById = expenseTypeDao.getExpenseTypeNameById(id);

        stringRedisTemplate.opsForValue().set(key,expenseTypeNameById);

        dataMap.put("name",expenseTypeNameById);
        return new JSONObject(dataMap);
    }
}
