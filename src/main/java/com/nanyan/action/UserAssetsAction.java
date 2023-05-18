package com.nanyan.action;


import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.UserAssetsService;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Controller
@Scope("prototype")// 多例
@Namespace("/user")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class UserAssetsAction {

    int page;
    int limit;

    int assetsId;
    String assetsName;
    String assetsLocation;
    Double assetsPrice;
    String assetsRemark;
    int assetsOwnerId;
    String assetsOwnerName;


    private JSONObject jsonObject;

    @Autowired
    UserAssetsService userAssetsService;

    @Action(value = "getAssetsListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getAssetsListByPage(){
        jsonObject = userAssetsService.getAssetsListByPage(page, limit);
        return SUCCESS;
    }

    @Action(value = "getUserAssetsListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getUserAssetsListByPage(){
        jsonObject = userAssetsService.getUserAssetsListByPage(page, limit);
        return SUCCESS;
    }

    @Action(value = "addAssets",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String addAssets(){
        jsonObject = userAssetsService.addAssets(assetsName,assetsLocation,assetsPrice,assetsRemark,assetsOwnerId,assetsOwnerName);
        return SUCCESS;
    }

    @Action(value = "updateAssets",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String updateAssets(){
        jsonObject = userAssetsService.updateAssets(assetsId,assetsName,assetsLocation,assetsPrice,assetsRemark,assetsOwnerId,assetsOwnerName);
        return SUCCESS;
    }

    @Action(value = "updateAssetsDeleted",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String updateDeleted(){
        jsonObject = userAssetsService.updateDeleted(assetsId);
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

    public int getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(int assetsId) {
        this.assetsId = assetsId;
    }

    public String getAssetsName() {
        return assetsName;
    }

    public void setAssetsName(String assetsName) {
        this.assetsName = assetsName;
    }

    public String getAssetsLocation() {
        return assetsLocation;
    }

    public void setAssetsLocation(String assetsLocation) {
        this.assetsLocation = assetsLocation;
    }

    public Double getAssetsPrice() {
        return assetsPrice;
    }

    public void setAssetsPrice(Double assetsPrice) {
        this.assetsPrice = assetsPrice;
    }

    public String getAssetsRemark() {
        return assetsRemark;
    }

    public void setAssetsRemark(String assetsRemark) {
        this.assetsRemark = assetsRemark;
    }

    public int getAssetsOwnerId() {
        return assetsOwnerId;
    }

    public void setAssetsOwnerId(int assetsOwnerId) {
        this.assetsOwnerId = assetsOwnerId;
    }

    public String getAssetsOwnerName() {
        return assetsOwnerName;
    }

    public void setAssetsOwnerName(String assetsOwnerName) {
        this.assetsOwnerName = assetsOwnerName;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
