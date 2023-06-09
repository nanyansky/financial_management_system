package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.OperationLogService;
import com.nanyan.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/1 16:55
 */
@Controller
@Scope("prototype")// 多例
@Namespace("/log")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class OperationLogAction extends ActionSupport {

    private int page;
    private int limit;
    HttpSession session = ServletActionContext.getRequest().getSession();
    private String userName;

    private Timestamp startTime;
    private Timestamp endTime;

    private JSONObject jsonObject;

    @Autowired
    private OperationLogService operationLogService;

    @Action(value = "getLogListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String getLogListByPage(){
        jsonObject = operationLogService.getLogListByPage(page,limit);
        return SUCCESS;
    }

    @Action(value = "getLogList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String getLogList(){
        jsonObject = operationLogService.getLogList();
        return SUCCESS;
    }

    @Action(value = "getLogListByUserName",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String getLogListByUserName(){
        jsonObject = operationLogService.getLogListByUserName(userName,page,limit);
        return SUCCESS;
    }

    @Action(value = "searchOperationLog",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String searchOperationLog(){
        jsonObject = operationLogService.searchOperationLog(userName,startTime,endTime,page,limit);
        return SUCCESS;
    }

    @Action(value = "getTop10OperationLog",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String getTop10OperationLog(){
        jsonObject = operationLogService.getTop10OperationLog();
        return SUCCESS;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
