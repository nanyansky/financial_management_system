package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.IncomeTypeService;
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
public class IncomeTypeAction extends ActionSupport {
    private int id;
    private String name;

    private int page;
    private int limit;

    private JSONObject jsonObject;

    @Autowired
    IncomeTypeService incomeTypeService;

    @Action(value = "getIncomeTypeList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeTypeList(){
        jsonObject = incomeTypeService.getIncomeTypeList();
        return SUCCESS;
    }

    @Action(value = "getIncomeTypeListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeTypeListByPage(){
        jsonObject = incomeTypeService.getIncomeTypeListByPage(page,limit);
        return SUCCESS;
    }

    @Action(value = "getIncomeTypeListByName",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeTypeListByName(){
        jsonObject = incomeTypeService.getIncomeTypeListByName(name,page,limit);
        return SUCCESS;
    }

    @Action(value = "addIncomeType",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String addIncomeType(){
        jsonObject = incomeTypeService.addIncomeType(name);
        return SUCCESS;
    }

    @Action(value = "deleteIncomeByTypeById",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String deleteIncomeByTypeById(){
        jsonObject = incomeTypeService.deleteIncomeByTypeById(id);
        return SUCCESS;
    }

    @Action(value = "editIncomeType",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String editIncomeType(){
        jsonObject = incomeTypeService.editIncomeType(id,name);
        return SUCCESS;
    }

    @Action(value = "getIncomeTypeNameById",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeTypeNameById(){
        System.out.println("要查询的id："+id);
        jsonObject = incomeTypeService.getIncomeTypeNameById(id);
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
