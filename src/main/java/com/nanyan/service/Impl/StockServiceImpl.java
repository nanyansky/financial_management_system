package com.nanyan.service.Impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.dao.ExpenseDao;
import com.nanyan.dao.IncomeDao;
import com.nanyan.dao.StockDao;
import com.nanyan.entity.Expense;
import com.nanyan.entity.Income;
import com.nanyan.entity.User;
import com.nanyan.entity.UserStock;
import com.nanyan.service.ExpenseService;
import com.nanyan.service.IncomeService;
import com.nanyan.service.StockService;
import com.nanyan.service.StockTradeService;
import com.nanyan.utils.OperationType;
import com.nanyan.utils.StockDate;
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
import java.util.concurrent.TimeUnit;

@Service
public class StockServiceImpl implements StockService {

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Resource
    StringRedisTemplate stringRedisTemplate;


    @Autowired
    ExpenseService expenseService;

    @Autowired
    IncomeService incomeService;

    @Autowired
    StockDao stockDao;

    @Autowired
    StockTradeService stockTradeService;

    /**
     * 获取股票列表
     * @param sort
     * @param node
     * @param pageSize
     * @param pageIndex
     * @param asc
     * @param industryCode
     * @return
     */
    @Override
    public JSONObject getStockList(String sort, String node, int pageSize, int pageIndex, int asc, String industryCode) {

        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("sort",sort);
        requestBody.put("node",node);
        requestBody.put("pageSize",pageSize);
        requestBody.put("pageIndex",pageIndex);
        requestBody.put("asc",asc);
        requestBody.put("industryCode",industryCode);

        return StockDate.stockRank(requestBody);
    }

    /**
     * 获取用户股票列表
     * @param page
     * @param limit
     * @return
     */
    @Override
    public JSONObject getUserStockListByPage(int page, int limit ) {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //获取userStock数量
            int userStockNumber = stockDao.getUserStockNumber();
            //获取userStock列表
            List<UserStock> userStockListByPage = stockDao.getUserStockListByPage(page, limit);

            Map<String,Object> tmpMap = new HashMap<>();
            for (int i = 0; i < userStockListByPage.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(userStockListByPage.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",userStockNumber);
            dataMap.put("data",tmpMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    /**
     * 购买股票
     * @param code
     * @param name
     * @param price
     * @param stockNum
     * @return
     */
    @OptLog(content = "用户购买股票",operationType = OperationType.UPDATE)
    @Override
    public JSONObject buyStock(String code,String name,String price,int stockNum) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        double newPrice = Double.parseDouble(price);
        //购买股票花的钱
        double costMoney = newPrice * stockNum * 100;

        Map<String, Object> dataMap = new HashMap<>();

        //封装购买股票对象
        UserStock userStock = new UserStock();
        userStock.setStockCode(code);
        userStock.setStockName(name);
        userStock.setStockPrice(newPrice);
        userStock.setStockNum(stockNum);
        userStock.setStockUserId(user.getId());
        userStock.setStockUser(user.getUserName());
        userStock.setStockTime(new Timestamp(System.currentTimeMillis()));
        //添加到我的股票表
        stockDao.stockTrade(userStock);

        //添加支出记录
        String expenseContent = "购买" + name + "股票"+stockNum+"手，花费" + costMoney + "元。购买时价格为："+price+"元。";
        //添加支出记录到表
        expenseService.addExpense(user.getUserName(),6,new Timestamp(System.currentTimeMillis()),expenseContent,costMoney);

        //添加到交易记录表
        stockTradeService.save(user.getUserName(),user.getId(),code,name,newPrice,stockNum,costMoney,new Timestamp(System.currentTimeMillis()),1);


        dataMap.put("code",1);
        dataMap.put("message","购买成功");
        return new JSONObject(dataMap);
    }

    /**
     * 卖出股票
     * @param code
     * @param name
     * @param price
     * @param stockNum
     * @return
     */
    @OptLog(content = "用户出售股票",operationType = OperationType.UPDATE)
    @Override
    public JSONObject sellStock(int stockId,String code,String name,String price,String curPrice,int stockNum, int sellNum) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        double newPrice = Double.parseDouble(price);
        double newCurPrice = Double.parseDouble(curPrice);
        //卖出股票得到的钱
        double gainMoney = newCurPrice * sellNum * 100;
        //剩余股票数量
        int newStockNum = stockNum - sellNum;

        Map<String, Object> dataMap = new HashMap<>();

        //修改我的股票表
        UserStock userStock = new UserStock();
        userStock.setStockId(stockId);
        userStock.setStockCode(code);
        userStock.setStockName(name);
        userStock.setStockPrice(newPrice);
        userStock.setStockNum(newStockNum);
        userStock.setStockUserId(user.getId());
        userStock.setStockUser(user.getUserName());
        userStock.setStockTime(new Timestamp(System.currentTimeMillis()));

        stockDao.stockTrade(userStock);

        //添加收入记录
        String incomeContent = "出售" + name + "股票"+sellNum+"手，收入"+ gainMoney + "元。出售时价格为："+newCurPrice+"元。";
        //添加收入记录到表
        incomeService.addIncome(user.getUserName(),4,new Timestamp(System.currentTimeMillis()),incomeContent,gainMoney);

        // 添加到交易记录表
        stockTradeService.save(user.getUserName(),user.getId(),code,name,newPrice,sellNum,gainMoney,new Timestamp(System.currentTimeMillis()),0);

        dataMap.put("code",1);
        dataMap.put("message","出售成功");
        return new JSONObject(dataMap);
    }

    /**
     * 获取股票行业
     * @return JSONObject
     */
    @Override
    public JSONObject getStockIndustry() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //Redis中的key
        String key = "stockIndustry";
        //1.从缓存中取分类
        String industryJson = stringRedisTemplate.opsForValue().get(key);
        //2.判断是否存在
        if (industryJson != null) {
            //3.存在，直接返回
            return JSON.parseObject(industryJson);
        }

        //4.不存在，查询数据库，并写入缓存
        String s = HttpUtil.get("https://api.doctorxiong.club/v1/stock/industry/rank").toString();

        stringRedisTemplate.opsForValue().set(key,s);
        //设置过期时间为1天
        stringRedisTemplate.expire(key,86400, TimeUnit.SECONDS);

        return JSON.parseObject(s);
    }


}
