package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
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
import com.nanyan.utils.StockDate;
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
public class StockServiceImpl implements StockService {

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Autowired
    ExpenseService expenseService;

    @Autowired
    IncomeService incomeService;

    @Autowired
    StockDao stockDao;

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
        //添加购买记录到表
        stockDao.buyStock(userStock);

        //添加支出记录
        String expenseContent = "购买" + name + "股票"+stockNum+"手，花费" + costMoney + "元。购买时价格为："+price+"元。";
        //添加支出记录到表
        expenseService.addExpense(user.getUserName(),6,new Timestamp(System.currentTimeMillis()),expenseContent,costMoney);

        dataMap.put("code",1);
        dataMap.put("message","购买成功");
        return new JSONObject(dataMap);
    }



}
