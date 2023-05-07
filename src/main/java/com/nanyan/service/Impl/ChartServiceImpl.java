package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanyan.dao.ChartDao;
import com.nanyan.entity.chart.ExpenseCountChart;
import com.nanyan.entity.chart.ExpenseTypeChart;
import com.nanyan.entity.chart.IncomeCountChart;
import com.nanyan.entity.chart.IncomeTypeChart;
import com.nanyan.service.ChartService;
import com.nanyan.utils.GetSevenDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/6 21:47
 */
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    ChartDao chartDao;

    @Override
    public JSONObject getIncomeCount(String username) {
        HashMap<String, Object> dataMap = new HashMap<>();

        List<IncomeCountChart> incomeCount = chartDao.getIncomeCount(username);
        List<String> date = GetSevenDate.getDate();
        List<IncomeCountChart> tmpList = new ArrayList<>();
        for (String s : date) {
            IncomeCountChart incomeCountChart = new IncomeCountChart();
            incomeCountChart.setDate(s);
            boolean bool = false;

            //创建内循环 根据查询出已有的数量 循环次数
            for (IncomeCountChart countChart : incomeCount) {
                if (s.equals(countChart.getDate())) {
                    incomeCountChart.setCount(countChart.getCount());
                    incomeCountChart.setDate(countChart.getDate());
                    incomeCountChart.setMoney(countChart.getMoney());
                    bool = true;
                    break;
                }
            }
            if (!bool) {
                incomeCountChart.setCount(0);
                incomeCountChart.setMoney(0);
            }
            tmpList.add(incomeCountChart);
        }

        Map<String,Object> tmpMap = new HashMap<>();
        for (int i = 0; i < tmpList.size(); i++) {
            tmpMap.put(String.valueOf(i), JSON.toJSON(tmpList.get(i)));
        }
//        System.out.println(tmpMap);
        dataMap.put("data",tmpMap);
        return new JSONObject(dataMap);
    }

    @Override
    public JSONObject getExpenseCount(String username) {
        HashMap<String, Object> dataMap = new HashMap<>();

        List<ExpenseCountChart> expenseCount = chartDao.getExpenseCount(username);
        List<String> date = GetSevenDate.getDate();
        List<ExpenseCountChart> tmpList = new ArrayList<>();
        for (String s : date) {
            ExpenseCountChart expenseCountChart = new ExpenseCountChart();
            expenseCountChart.setDate(s);
            boolean bool = false;

            //创建内循环 根据查询出已有的数量 循环次数
            for (ExpenseCountChart countChart : expenseCount) {
                if (s.equals(countChart.getDate())) {
                    expenseCountChart.setCount(countChart.getCount());
                    expenseCountChart.setDate(countChart.getDate());
                    expenseCountChart.setMoney(countChart.getMoney());
                    bool = true;
                    break;
                }
            }
            if (!bool) {
                expenseCountChart.setCount(0);
                expenseCountChart.setMoney(0);
            }
            tmpList.add(expenseCountChart);
        }

        Map<String,Object> tmpMap = new HashMap<>();
        for (int i = 0; i < tmpList.size(); i++) {
            tmpMap.put(String.valueOf(i), JSON.toJSON(tmpList.get(i)));
        }
//        System.out.println(tmpMap);
        dataMap.put("data",tmpMap);
        return new JSONObject(dataMap);
    }

    @Override
    public JSONObject getIncomeTypeData(String username) {
        List<IncomeTypeChart> incomeTypeData = chartDao.getIncomeTypeData(username);

//        List<List<Map<String,Object>>> sumList = new ArrayList<>();
        List<Map<String,Object>> countList = new ArrayList<>();
        List<Map<String,Object>> moneyList = new ArrayList();

        Map<String,Object> sumMap = new HashMap<>();

        for (IncomeTypeChart in:incomeTypeData) {
            Map<String,Object> map1 = new HashMap<>();
            Map<String,Object> map2 = new HashMap<>();
            map1.put("value",in.getTypeCount());
            map1.put("name",in.getTypeName());

            map2.put("value",in.getTypeMoney());
            map2.put("name",in.getTypeName());
            countList.add(map1);
            moneyList.add(map2);
        }
//
//        sumList.add(countList);
//        sumList.add(moneyList);

        sumMap.put("incomeCount",countList);
        sumMap.put("incomeMoney",moneyList);

//        System.out.println(sumMap);
        return new JSONObject(new JSONObject(sumMap));
    }

    @Override
    public JSONObject getExpenseTypeData(String username) {
        List<ExpenseTypeChart> expenseTypeData = chartDao.getExpenseTypeData(username);

//        List<List<Map<String,Object>>> sumList = new ArrayList<>();
        List<Map<String,Object>> countList = new ArrayList<>();
        List<Map<String,Object>> moneyList = new ArrayList();

        Map<String,Object> sumMap = new HashMap<>();

        for (ExpenseTypeChart ex:expenseTypeData) {
            Map<String,Object> map1 = new HashMap<>();
            Map<String,Object> map2 = new HashMap<>();
            map1.put("value",ex.getTypeCount());
            map1.put("name",ex.getTypeName());

            map2.put("value",ex.getTypeMoney());
            map2.put("name",ex.getTypeName());

            countList.add(map1);
            moneyList.add(map2);
        }
//
//        sumList.add(countList);
//        sumList.add(moneyList);

        sumMap.put("expenseCount",countList);
        sumMap.put("expenseMoney",moneyList);

//        System.out.println(sumMap);
        return new JSONObject(new JSONObject(sumMap));
    }
}
