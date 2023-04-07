package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.ChartService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/6 21:50
 */

@Controller
@Scope("prototype")// 多例
@Namespace("/")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class ChartAction extends ActionSupport {

    @Resource
    ChartService chartService;
    private JSONObject jsonObject;

    @Action(value = "getIncomeData",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeData(){
        jsonObject = chartService.getIncomeCount();
        return SUCCESS;
    }

    @Action(value = "getExpenseData",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseData(){
        jsonObject = chartService.getExpenseCount();
        return SUCCESS;
    }

    @Action(value = "getIncomeTypeData",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeTypeData(){
        jsonObject = chartService.getIncomeTypeData();
        return SUCCESS;
    }

    @Action(value = "getExpenseTypeData",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseTypeData(){
        jsonObject = chartService.getExpenseTypeData();
        return SUCCESS;
    }



    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
