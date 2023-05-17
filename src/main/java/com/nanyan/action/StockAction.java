package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.service.StockService;
import com.nanyan.service.StockTradeService;
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

    private String code;
    private String name;
    private String price;


    private int stockId;
    private String stockCode;
    private String stockName;
    private String stockPrice;
    private String curStockPrice;
    private int stockNum;
    private int sellStockNum;
    private int tradeType;


    private String industryCode;
    private JSONObject jsonObject;


    @Autowired
    StockService stockService;

    @Autowired
    StockTradeService stockTradeService;

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
        jsonObject = stockService.buyStock(code,name,price,stockNum);
        return SUCCESS;
    }

    @Action(value = "sellStock",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String sellStock(){
        jsonObject = stockService.sellStock(stockId,stockCode,stockName,stockPrice,curStockPrice,stockNum,sellStockNum);
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

    @Action(value = "getStockIndustry",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getStockIndustry(){
        jsonObject = stockService.getStockIndustry();
        return SUCCESS;
    }

    @Action(value = "getTradeListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getTradeListByPage(){
        jsonObject = stockTradeService.getTradeListByPage(page,limit);
        return SUCCESS;
    }

    @Action(value = "searchTradList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String searchTradList(){
        jsonObject = stockTradeService.searchTradList(page,limit,tradeType);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getCurStockPrice() {
        return curStockPrice;
    }

    public void setCurStockPrice(String curStockPrice) {
        this.curStockPrice = curStockPrice;
    }

    public int getSellStockNum() {
        return sellStockNum;
    }

    public void setSellStockNum(int sellStockNum) {
        this.sellStockNum = sellStockNum;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public int getStockNum() {
        return stockNum;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
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
