package com.nanyan.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanyan.entity.User;
import com.nanyan.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nanyan
 * @version 1.0
 * @description: 与用户有关的操作
 * @date 2023/3/17 16:13
 */



@Controller
@Slf4j
@Scope("prototype")// 多例
@Namespace("/user")// 对应配置文件中的每个action的name
@ParentPackage("json-default")
public class UserAction extends ActionSupport {

    HttpServletRequest request = ServletActionContext.getRequest();
    private String username;
    private String password;

    private Map<String, Object> dataMap = new HashMap<String, Object>();
    private JSONObject jsonObject;



    @Autowired
    private UserService userService;


    @Action(value = "save", results = {
            @Result(name = "success", location = "/home.jsp")
    })
    public String save(){
        Timestamp d = new Timestamp(System.currentTimeMillis());
        User user = new User();
        user.setUserName("test2");
        user.setPassword("123");
        user.setIsAdmin(0);
        user.setIsDeleted(0);
        user.setRegisterTime(d);

        userService.save(user);
        return "success";
    }

    @Action(value = "rmUser",results = {@Result(type = "json",params = {"root","jsonObject"})})
    public String rmUser(){
        return SUCCESS;
    }

    @Action(value = "getUser", results = {
            @Result(type = "json",params = {"root","jsonObject"})
    })
    public String getUser(){
//        User user = userService.getUser(1);
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user);
        jsonObject = (JSONObject) JSON.toJSON(user);
        System.out.println(jsonObject);
        return SUCCESS;
    }

    @Action(value = "userLogin", results = {
            @Result(type = "json",params = {"root","jsonObject"})
    })
    public String userLogin(){
        //调试用，优化时删除
        log.info(username +" + "+password);
        dataMap.put("username",username);
        dataMap.put("password",password);



        User tmpUser = userService.findByUserName(username);
        //用户不存在
        if(tmpUser == null){
            dataMap.put("code",0);
            dataMap.put("message","用户不存在！");
            jsonObject = new JSONObject(dataMap);
            log.info(String.valueOf(jsonObject));
            return SUCCESS;
        }

        //登录成功
        if(tmpUser.getPassword().equals(password)){
            request.getSession().setAttribute("user",tmpUser);
            dataMap.put("code",1);
            dataMap.put("message","登录成功！");
            jsonObject = new JSONObject(dataMap);
            System.out.println(jsonObject);
            return SUCCESS;
        }
        else {
            dataMap.put("code",0);
            dataMap.put("message","密码错误！");
            jsonObject = new JSONObject(dataMap);
            System.out.println(jsonObject);
            return SUCCESS;
        }
    }


    @Action(value = "logout",results = {@Result(type = "json",params = {"root","jsonObject"})})
    public String logOut(){
        request.getSession().removeAttribute("user");
        dataMap.put("code",1);
        dataMap.put("message","登出成功！");
        jsonObject = new  JSONObject(dataMap);
        return SUCCESS;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //会把本类所有getter方法序列化成字符串返回给jsp页面
    public Map<String, Object> getDataMap() {
        return dataMap;
    }
    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

}
