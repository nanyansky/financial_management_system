package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.InitMenusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/5 12:55
 */
@Service
public class InitMenusServiceImpl implements InitMenusService {
    @Override
    public JSONObject returnMenus(int isAdmin) throws IOException {

        String admin = "{\"homeInfo\":{\"title\":\"首页\",\"href\":\"/index.jsp\"},\"logoInfo\":{\"title\":\"家庭理财系统\",\"image\":\"statics/layui/images/logo.png\",\"href\":\"\"},\"menuInfo\":[{\"title\":\"常规管理\",\"icon\":\"fa fa-address-book\",\"href\":\"\",\"target\":\"_self\",\"child\":[{\"title\":\"用户管理\",\"href\":\"/admin/userTable.jsp\",\"icon\":\"fa fa-user\",\"target\":\"_self\"},{\"title\":\"收入管理\",\"href\":\"/admin/income.jsp\",\"icon\":\"fa fa-wallet\",\"target\":\"_self\"},{\"title\":\"支出管理\",\"href\":\"/admin/expense.jsp\",\"icon\":\"fas fa-hand-holding-usd\",\"target\":\"_self\"},{\"title\":\"支出类别管理\",\"href\":\"/admin/expense_type.jsp\",\"icon\":\"fa fa-list-alt\",\"target\":\"_self\"},{\"title\":\"收入类别管理\",\"href\":\"/admin/income_type.jsp\",\"icon\":\"fa fa-list-alt\",\"target\":\"_self\"}]},{\"title\":\"收入管理\",\"icon\":\"fa fa-wallet\",\"href\":\"\",\"target\":\"_self\",\"child\":[{\"title\":\"收入列表\",\"href\":\"/user/incomeTable.jsp\",\"icon\":\"fa fa-list-alt\",\"target\":\"_self\"},{\"title\":\"我的收入\",\"href\":\"/user/my-income.jsp\",\"icon\":\"fa fa-wallet\",\"target\":\"_self\"},{\"title\":\"我的收入统计\",\"href\":\"/user/my-income-stats.jsp\",\"icon\":\"fa fa-pie-chart\",\"target\":\"_self\"}]},{\"title\":\"支出管理\",\"icon\":\"fas fa-hand-holding-usd\",\"href\":\"\",\"target\":\"_self\",\"child\":[{\"title\":\"支出列表\",\"href\":\"/user/expenseTable.jsp\",\"icon\":\"fa fa-list-alt\",\"target\":\"_self\"},{\"title\":\"我的支出\",\"href\":\"/user/my-expense.jsp\",\"icon\":\"fas fa-hand-holding-usd\",\"target\":\"_self\"},{\"title\":\"我的支出统计\",\"href\":\"/user/my-expense-stats.jsp\",\"icon\":\"fa fa-pie-chart\",\"target\":\"_self\"}]},{\"title\":\"统计\",\"href\":\"/user/stats.jsp\",\"icon\":\"fa fa-pie-chart\",\"target\":\"_self\"},{\"title\":\"日志\",\"href\":\"/user/log_list.jsp\",\"icon\":\"fa fad fa-blog\",\"target\":\"_self\"}]}";
        String user = "{\"homeInfo\":{\"title\":\"首页\",\"href\":\"/index.jsp\"},\"logoInfo\":{\"title\":\"家庭理财系统\",\"image\":\"statics/layui/images/logo.png\",\"href\":\"\"},\"menuInfo\":[{\"title\":\"收入管理\",\"icon\":\"fa fa-wallet\",\"href\":\"\",\"target\":\"_self\",\"child\":[{\"title\":\"收入列表\",\"href\":\"/user/incomeTable.jsp\",\"icon\":\"fa fa-list-alt\",\"target\":\"_self\"},{\"title\":\"我的收入\",\"href\":\"/user/my-income.jsp\",\"icon\":\"fa fa-wallet\",\"target\":\"_self\"},{\"title\":\"我的收入统计\",\"href\":\"/user/my-income-stats.jsp\",\"icon\":\"fa fa-pie-chart\",\"target\":\"_self\"}]},{\"title\":\"支出管理\",\"icon\":\"fas fa-hand-holding-usd\",\"href\":\"\",\"target\":\"_self\",\"child\":[{\"title\":\"支出列表\",\"href\":\"/user/expenseTable.jsp\",\"icon\":\"fa fa-list-alt\",\"target\":\"_self\"},{\"title\":\"我的支出\",\"href\":\"/user/my-expense.jsp\",\"icon\":\"fas fa-hand-holding-usd\",\"target\":\"_self\"},{\"title\":\"我的支出统计\",\"href\":\"/user/my-expense-stats.jsp\",\"icon\":\"fa fa-pie-chart\",\"target\":\"_self\"}]},{\"title\":\"统计\",\"href\":\"/user/stats.jsp\",\"icon\":\"fa fa-pie-chart\",\"target\":\"_self\"},{\"title\":\"日志\",\"href\":\"/user/log_list.jsp\",\"icon\":\"fa fad fa-blog\",\"target\":\"_self\"}]}";

        if(isAdmin == 1){
            return JSONObject.parseObject(admin);
        }
        else {
            return JSONObject.parseObject(user);
        }
    }
}
