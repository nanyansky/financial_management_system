package com.nanyan.action;

import com.alibaba.fastjson.JSONObject;
import com.nanyan.entity.User;
import com.nanyan.service.InitMenusService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/5 13:01
 */
@Controller
@Scope("prototype")// 多例
@Namespace("/")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class InitMenusAction extends ActionSupport {

    private JSONObject jsonObject;

    HttpSession session = ServletActionContext.getRequest().getSession();
    @Resource
    InitMenusService initMenusService;

    @Action(value = "returnMenus", results = {
            @Result(type = "json",params = {"root","jsonObject"})})
    public String returnMenus(){
        User user = (User) session.getAttribute("user");
        jsonObject = initMenusService.returnMenus(user.getIsAdmin());
        return SUCCESS;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

}
