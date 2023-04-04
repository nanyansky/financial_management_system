package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.IncomeService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.jws.Oneway;
import java.sql.Timestamp;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 11:49
 */

@Controller
@Scope("prototype")// 多例
@Namespace("/income")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class IncomeAction extends ActionSupport {
    private int id;
    private String userName;
    private int incomeTypeId;
    private Timestamp incomeTime;
    private Timestamp createTime;
    private String incomeContent;
    private double incomeAmount;
    private int isDeleted;


    private Timestamp startTime;
    private Timestamp endTime;

    private int page;
    private int limit;

    private JSONObject jsonObject;
    @Autowired
    IncomeService incomeService;


    @Action(value = "getIncomeList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeList(){
        jsonObject = incomeService.getIncomeList();
        return SUCCESS;
    }

    @Action(value = "getIncomeListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeListByPage(){
        jsonObject = incomeService.getIncomeListByPage(page,limit);
        return SUCCESS;
    }

    @Action(value = "getIncomeListByUserName",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeListByUserName(){
        jsonObject = incomeService.getIncomeListByUserName(userName,page,limit);
        return SUCCESS;
    }

    @Action(value = "searchIncome",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String searchIncome(){
        jsonObject = incomeService.searchIncome(userName,incomeTypeId,startTime,endTime,page,limit);
        return SUCCESS;
    }

    @Action(value = "addIncome",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String addIncome(){
        jsonObject = incomeService.addIncome(userName,incomeTypeId,incomeTime,incomeContent,incomeAmount);
        return SUCCESS;
    }

    @Action(value = "deleteIncomeById",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String deleteIncomeById(){
        jsonObject = incomeService.deleteIncomeById(id);
        return SUCCESS;
    }

    @Action(value = "editIncome",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String editIncome(){
        jsonObject = incomeService.editIncome(userName,id,incomeTypeId,incomeTime,incomeContent,incomeAmount,isDeleted);
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
    public Timestamp getIncomeTime() {
        return incomeTime;
    }
    public void setIncomeTime(Timestamp incomeTime) {
        this.incomeTime = incomeTime;
    }
    public double getIncomeAmount() {
        return incomeAmount;
    }
    public void setIncomeAmount(double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public int getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(int incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getIncomeContent() {
        return incomeContent;
    }

    public void setIncomeContent(String incomeContent) {
        this.incomeContent = incomeContent;
    }

    public int getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
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
