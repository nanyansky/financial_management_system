package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.dao.IncomeTypeDao;
import com.nanyan.entity.IncomeType;
import com.nanyan.service.IncomeTypeService;
import com.nanyan.utils.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class IncomeTypeServiceImpl implements IncomeTypeService {

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Autowired
    IncomeTypeDao incomeTypeDao;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public JSONObject getIncomeTypeList() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //获取Income数量
            int incomeTypeNumber = incomeTypeDao.getIncomeTypeNumber();
            //获取Income列表
            List<IncomeType> incomeTypeList = incomeTypeDao.getIncomeTypeList();

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < incomeTypeList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(incomeTypeList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",incomeTypeNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getIncomeTypeListByPage(int page, int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //获取Income数量
            int incomeTypeNumber = incomeTypeDao.getIncomeTypeNumber();
            //获取Income列表
            List<IncomeType> incomeTypeList = incomeTypeDao.getIncomeTypeListByPage(page,limit);

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < incomeTypeList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(incomeTypeList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",incomeTypeNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    public JSONObject getIncomeTypeListByName(String name, int page, int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<IncomeType> list = incomeTypeDao.getIncomeTypeListByName(name,page,limit);

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
    @OptLog(content = "添加收入分类", operationType = OperationType.INSERT)
    public JSONObject addIncomeType(String name) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            IncomeType tmpIncomeType = incomeTypeDao.getIncomeTypeByName(name);


            if (tmpIncomeType == null) {
                IncomeType incomeType = new IncomeType();

                incomeType.setName(name);
                incomeType.setCreateTime(new Timestamp(System.currentTimeMillis()));

                incomeTypeDao.addIncomeType(incomeType);

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
    @OptLog(content = "删除收入分类", operationType = OperationType.DELETE)
    public JSONObject deleteIncomeByTypeById(int id) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            incomeTypeDao.deleteIncomeByTypeById(id);

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
    @OptLog(content = "编辑收入分类", operationType = OperationType.UPDATE)
    public JSONObject editIncomeType(int id, String name) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            IncomeType tmpIncomeType = incomeTypeDao.getIncomeTypeByName(name);


            if (tmpIncomeType == null || tmpIncomeType.getId() == id) {
                IncomeType incomeType = new IncomeType();
                //添加用户id
                incomeType.setName(name);

                incomeTypeDao.editIncomeType(id,incomeType);

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
    public JSONObject getIncomeTypeNameById(int id) {
        Map<String, Object> dataMap = new HashMap<String, Object>();

        //Redis中的key
        String key = "incomeTypeId:"+id;
        //1.从缓存中取分类
        String name = stringRedisTemplate.opsForValue().get(key);


        //2.判断是否存在
        if (name != null) {
            //3.存在，直接返回
            dataMap.put("name",name);
            return new JSONObject(dataMap);
        }

        //4.不存在，查找数据库，并加入缓存
        String incomeTypeNameById = incomeTypeDao.getIncomeTypeNameById(id);

        stringRedisTemplate.opsForValue().set(key,incomeTypeNameById);
        dataMap.put("name",incomeTypeNameById);
        return new JSONObject(dataMap);
    }
}
