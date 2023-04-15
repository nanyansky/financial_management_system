package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.entity.User;
import com.nanyan.service.ChartService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
    HttpSession session = ServletActionContext.getRequest().getSession();
    private String userName;

    @Action(value = "getIncomeData",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeData(){
        jsonObject = chartService.getIncomeCount(userName);
        return SUCCESS;
    }

    @Action(value = "getExpenseData",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseData(){
        jsonObject = chartService.getExpenseCount(userName);
        return SUCCESS;
    }

    @Action(value = "getIncomeTypeData",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getIncomeTypeData(){
        jsonObject = chartService.getIncomeTypeData(userName);
        return SUCCESS;
    }

    @Action(value = "getExpenseTypeData",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseTypeData(){
        jsonObject = chartService.getExpenseTypeData(userName);
        return SUCCESS;
    }



    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
