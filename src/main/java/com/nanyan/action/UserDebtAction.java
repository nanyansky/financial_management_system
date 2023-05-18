package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.UserDebtService;
import com.nanyan.service.UserDebtService;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Controller
@Scope("prototype")// 多例
@Namespace("/user")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class UserDebtAction {

    int page;
    int limit;

    int debtId;
    String debtName;
    int debtType;
    String debtLocation;
    Double debtPrice;
    String debtRemark;
    int debtOwnerId;
    String debtOwnerName;
    int debtStatus;

    private JSONObject jsonObject;

    @Autowired
    UserDebtService userDebtService;


    @Action(value = "getDebtListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getDebtListByPage(){
        jsonObject = userDebtService.getDebtListByPage(page, limit);
        return SUCCESS;
    }

    @Action(value = "getUserDebtListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getUserDebtListByPage(){
        jsonObject = userDebtService.getUserDebtListByPage(page, limit);
        return SUCCESS;
    }

    @Action(value = "addDebt",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String addDebt(){
        jsonObject = userDebtService.addDebt(debtName,debtStatus,debtType,debtPrice,debtRemark,debtOwnerId,debtOwnerName);
        return SUCCESS;
    }

    @Action(value = "updateDebt",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String updateDebt(){
        jsonObject = userDebtService.updateDebt(debtId,debtName,debtType,debtPrice,debtRemark,debtOwnerId,debtOwnerName,debtStatus);
        return SUCCESS;
    }

    @Action(value = "updateStatus",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String updateStatus(){
        jsonObject = userDebtService.updateStatus(debtId,debtStatus);
        return SUCCESS;
    }

    @Action(value = "updateDebtDeleted",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String updateDeleted(){
        jsonObject = userDebtService.updateDeleted(debtId);
        return SUCCESS;
    }

    @Action(value = "searchDebtList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String searchDebtList(){
        jsonObject = userDebtService.searchDebtList(debtType,page,limit);
        return SUCCESS;
    }
    @Action(value = "searchUserDebtList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String searchUserDebtList(){
        jsonObject = userDebtService.searchUserDebtList(debtType,page,limit);
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

    public int getDebtId() {
        return debtId;
    }

    public void setDebtId(int debtId) {
        this.debtId = debtId;
    }

    public String getDebtName() {
        return debtName;
    }

    public void setDebtName(String debtName) {
        this.debtName = debtName;
    }

    public int getDebtType() {
        return debtType;
    }

    public void setDebtType(int debtType) {
        this.debtType = debtType;
    }

    public String getDebtLocation() {
        return debtLocation;
    }

    public void setDebtLocation(String debtLocation) {
        this.debtLocation = debtLocation;
    }

    public Double getDebtPrice() {
        return debtPrice;
    }

    public void setDebtPrice(Double debtPrice) {
        this.debtPrice = debtPrice;
    }

    public String getDebtRemark() {
        return debtRemark;
    }

    public void setDebtRemark(String debtRemark) {
        this.debtRemark = debtRemark;
    }

    public int getDebtOwnerId() {
        return debtOwnerId;
    }

    public void setDebtOwnerId(int debtOwnerId) {
        this.debtOwnerId = debtOwnerId;
    }

    public String getDebtOwnerName() {
        return debtOwnerName;
    }

    public void setDebtOwnerName(String debtOwnerName) {
        this.debtOwnerName = debtOwnerName;
    }

    public int getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(int debtStatus) {
        this.debtStatus = debtStatus;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
