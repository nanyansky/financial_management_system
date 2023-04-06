package com.nanyan.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;



import com.nanyan.entity.User;
import com.nanyan.service.UserService;

import com.nanyan.utils.VerifyCode;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.ServletActionContext;

import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;


/**
 * @author nanyan
 * @version 1.0
 * @description: 与用户有关的操作
 * @date 2023/3/17 16:13
 */



@Controller
@Scope("prototype")// 多例
@Namespace("/user")// 对应配置文件中的每个action的name
@ParentPackage("Interceptor")
public class UserAction extends ActionSupport {
    private int page;
    private int limit;
    HttpSession session = ServletActionContext.getRequest().getSession();
    private String userName;
    private String password;
    private String email;
    private String captcha;
    private String phoneNumber;
    private int isAdmin;
    private String sex;
    private int id;
    private int status;
    private String old_password;
    private String new_password;


    private JSONObject jsonObject;

    @Autowired
    private UserService userService;

    /**
     * @description: 用户注册
     * @param:
     * @return: java.lang.String
     * @author nanyan
     * @date:  15:50
     */
    @Action(value = "userRegister", results = {
            @Result(type = "json",params = {"root","jsonObject"})})
    public String userRegister(){
        jsonObject = userService.userRegister(userName, password, email,sex, phoneNumber, captcha);
        return SUCCESS;
    }


    /**
     * @description: 登录Action
     * @param:  NONE
     * @return: java.lang.String
     * @author nanyan
     * @date:  21:23
     */
    @Action(value = "userLogin", results = {
            @Result(type = "json",params = {"root","jsonObject"})})
    public String userLogin(){
        jsonObject = userService.userLogin(userName,password,captcha);
        return SUCCESS;
    }

    /**
     * @description: 登出Action
     * @param:  NONE
     * @return: java.lang.String
     * @author nanyan
     * @date:  21:23
     */
    @Action(value = "logout",results = {@Result(type = "json",params = {"root","jsonObject"})})
    public String logOut(){
        User user = (User) session.getAttribute("user");
        String tmpUserName = user.getUserName();
        jsonObject = userService.userLogOut(tmpUserName);
        return SUCCESS;
    }

    /**
     * @description: 获取用户列表接口
     * @param:  NONE
     * @return: java.lang.String
     * @author nanyan
     * @date:  21:25
     */
    @Action(value = "getUserList",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getUserList(){
        jsonObject = userService.getUserList();
        return SUCCESS;
    }

    @Action(value = "getUserListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")})
    public String getUserListByPage(){
        jsonObject = userService.getUserListByPage(page,limit);
        return SUCCESS;
    }





    @Action(value = "addUser",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String addUser(){
        jsonObject = userService.addUser(userName,password,email,phoneNumber,isAdmin,sex);
        return SUCCESS;
    }

    @Action(value = "findByUserName",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String findByUserName(){
        jsonObject = userService.findByUserName(userName);
            return SUCCESS;
}

    @Action(value = "getUserListByUserName",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String getUserListByUserName(){
        jsonObject = userService.getUserListByUserName(userName,page,limit);
        return SUCCESS;
    }


    @Action(value = "searchUser",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String searchUser(){
        jsonObject = userService.searchUser(userName,isAdmin,status,page,limit);
        return SUCCESS;
    }

    @Action(value = "changeUserStatus",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String changeUserStatus(){
        jsonObject = userService.changeUserStatus(id,status);
        return SUCCESS;
    }

    @Action(value = "deleteById",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String deleteById(){
        jsonObject = userService.deleteById(id);
        return SUCCESS;
    }

    @Action(value = "editUser",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String editUser(){
        jsonObject = userService.editUser(userName,id,password,email,phoneNumber,isAdmin,sex);
        return SUCCESS;
    }



    @Action(value = "changeInfoByUsername",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String changeInfoByUsername(){
        jsonObject = userService.changeInfoByUsername(userName,phoneNumber,sex);
        return SUCCESS;
    }

    @Action(value = "changePwdByUsername",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String changePwdByUsername(){
        jsonObject = userService.changePwdByUsername(new_password,old_password);
        return SUCCESS;
    }

    @Action(value = "getVcode")
    public String getVerifyCode(){
        /*
         * 1. 创建验证码类
         */
        VerifyCode vc = new VerifyCode();
        /*
         * 2. 得到验证码图片
         */
        BufferedImage image = vc.getImage();
        /*
         * 3. 把图片上的文本保存到session中
         */
        session.removeAttribute("session_vcode");
        session.setAttribute("session_vcode", vc.getText());
        /*
         * 4. 把图片响应给客户端
         */
        try {
            VerifyCode.output(image, ServletActionContext.getResponse().getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //会把本类所有getter方法序列化成字符串返回给jsp页面

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getCaptcha() {
        return captcha;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

}
