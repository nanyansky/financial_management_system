package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.StockService;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Controller
@Scope("prototype")// 多例
@Namespace("/stock")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class StockAction {

    private String sort;
    private String node;
    private int page;
    private int limit;
    private int asc;
    private String industryCode;
    private JSONObject jsonObject;


    @Autowired
    StockService stockService;
    @Action(value = "getStockList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getExpenseList(){
        jsonObject = stockService.getStockList(sort, node, limit, page, asc,industryCode);
        return SUCCESS;
    }

    @Action(value = "buyStock",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String buyStock(){
        jsonObject = stockService.getStockList(sort, node, limit, page, asc,industryCode);
        return SUCCESS;
    }


    @Action(value = "stockDetail",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String stockDetail(){
        jsonObject = stockService.getStockList(sort, node, limit, page, asc,industryCode);
        return SUCCESS;
    }


    @Action(value = "getUserStockListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getUserStockListByPage(){
        jsonObject = stockService.getUserStockListByPage(page,limit);
        return SUCCESS;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
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

    public int getAsc() {
        return asc;
    }

    public void setAsc(int asc) {
        this.asc = asc;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
