package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.ExpenseService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 11:49
 */

@Controller
@Scope("prototype")// 多例
@Namespace("/expense")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class ExpenseAction extends ActionSupport {
    private int id;
    private String userName;
    private int expenseTypeId;

    private Timestamp expenseTime;
    private Timestamp createTime;
    private String expenseContent;
    private double expenseAmount;

    private Timestamp startTime;
    private Timestamp endTime;
    private int page;
    private int limit;

    private JSONObject jsonObject;
    @Autowired
    ExpenseService expenseService;


    @Action(value = "getExpenseList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseList(){
        jsonObject = expenseService.getExpenseList();
        return SUCCESS;
    }

    @Action(value = "getExpenseListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseListByPage(){
        jsonObject = expenseService.getExpenseListByPage(page,limit);
        return SUCCESS;
    }

    @Action(value = "getExpenseListByUserName",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseListByUserName(){
        jsonObject = expenseService.getExpenseListByUserName(userName,page,limit);
        return SUCCESS;
    }


    @Action(value = "searchExpense",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String searchExpense(){
        jsonObject = expenseService.searchExpense(userName,expenseTypeId,startTime,endTime,page,limit);
        return SUCCESS;
    }

    @Action(value = "addExpense",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String addExpense(){
        jsonObject = expenseService.addExpense(userName,expenseTypeId,expenseTime,expenseContent,expenseAmount);
        return SUCCESS;
    }

    @Action(value = "deleteExpenseById",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String deleteExpenseById(){
        jsonObject = expenseService.deleteExpenseById(id);
        return SUCCESS;
    }

    @Action(value = "editExpense",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String editExpense(){
        jsonObject = expenseService.editExpense(userName, id, expenseTypeId,expenseTime,expenseContent, expenseAmount);
        return SUCCESS;
    }





    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getExpenseTypeId() {
        return expenseTypeId;
    }
    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }
    public Timestamp getExpenseTime() {
        return expenseTime;
    }
    public void setExpenseTime(Timestamp expenseTime) {
        this.expenseTime = expenseTime;
    }
    public double getExpenseAmount() {
        return expenseAmount;
    }
    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getExpenseContent() {
        return expenseContent;
    }

    public void setExpenseContent(String expenseContent) {
        this.expenseContent = expenseContent;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
