package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanyan.dao.ChartDao;
import com.nanyan.entity.chart.ExpenseCountChart;
import com.nanyan.entity.chart.IncomeCountChart;
import com.nanyan.service.ChartService;
import com.nanyan.utils.GetSevenDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public JSONObject getIncomeCount() {
        HashMap<String, Object> dataMap = new HashMap<>();

        List<IncomeCountChart> incomeCount = chartDao.getIncomeCount();
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
    public JSONObject getExpenseCount() {
        HashMap<String, Object> dataMap = new HashMap<>();

        List<ExpenseCountChart> expenseCount = chartDao.getExpenseCount();
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
}
