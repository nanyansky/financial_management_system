package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.ExpenseTypeService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 19:31
 */
@Controller
@Scope("prototype")// 多例
@Namespace("/")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class ExpenseTypeAction extends ActionSupport {
    private int id;
    private String name;

    private int page;
    private int limit;

    private JSONObject jsonObject;

    @Autowired
    ExpenseTypeService expenseTypeService;

    @Action(value = "getExpenseTypeList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseTypeList(){
        jsonObject = expenseTypeService.getExpenseTypeList();
        return SUCCESS;
    }

    @Action(value = "getExpenseTypeListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseTypeListByPage(){
        jsonObject = expenseTypeService.getExpenseTypeListByPage(page,limit);
        return SUCCESS;
    }

    @Action(value = "getExpenseTypeListByName",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseTypeListByName(){
        jsonObject = expenseTypeService.getExpenseTypeListByName(name,page,limit);
        return SUCCESS;
    }

    @Action(value = "addExpenseType",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String addExpenseType(){
        jsonObject = expenseTypeService.addExpenseType(name);
        return SUCCESS;
    }

    @Action(value = "deleteExpenseByTypeById",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String deleteExpenseByTypeById(){
        jsonObject = expenseTypeService.deleteExpenseByTypeById(id);
        return SUCCESS;
    }

    @Action(value = "editExpenseType",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String editExpenseType(){
        jsonObject = expenseTypeService.editExpenseType(id,name);
        return SUCCESS;
    }

    @Action(value = "getExpenseTypeNameById",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseTypeNameById(){
        System.out.println("要查询的id："+id);
        jsonObject = expenseTypeService.getExpenseTypeNameById(id);
        return SUCCESS;
    }



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
