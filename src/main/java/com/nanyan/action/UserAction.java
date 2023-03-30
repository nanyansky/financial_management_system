package com.nanyan.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.nanyan.entity.Test;
import com.nanyan.entity.User;
import com.nanyan.service.UserService;
import com.nanyan.utils.VerifyCode;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nanyan
 * @version 1.0
 * @description: 与用户有关的操作
 * @date 2023/3/17 16:13
 */



@Controller
@Scope("prototype")// 多例
@Namespace("/user")// 对应配置文件中的每个action的name
@ParentPackage("user")
public class UserAction extends ActionSupport {

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    private int page;
    private int limit;
    HttpSession session = ServletActionContext.getRequest().getSession();
    private String userName;
    private String password;
    private String captcha;
    private String phoneNumber;
    private int isAdmin;
    private String sex;
    private int id;
    private int status;
    private String old_password;
    private String new_password;


    private User user;

    private Test test;
    public Test getTest() {
        return test;
    }
    public void setTest(Test test) {
        this.test = test;
    }

    private Map<String, Object> dataMap = new HashMap<String, Object>();
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
            @Result(type = "json",params = {"root","jsonObject"})
    })
    public String userRegister(){
        try {

            String sessionVcode = (String) session.getAttribute("session_vcode");
            if(!sessionVcode.equalsIgnoreCase(captcha.trim())){
                dataMap.put("code",0);
                dataMap.put("message","验证码错误！");
                jsonObject = new JSONObject(dataMap);
                return SUCCESS;
            }
            //查找是否存在同名用户
            User tmpUser = userService.findByUserName(userName);
            if(tmpUser == null){

                User newUser = new User();
                newUser.setUserName(userName);
                newUser.setPassword(password);
                newUser.setSex(sex);
                newUser.setPhoneNumber(phoneNumber);
                userService.addUser(newUser);

                dataMap.put("code",1);
                dataMap.put("message","注册成功！请等待管理员审核！");
                jsonObject = new JSONObject(dataMap);
                return SUCCESS;
            }
            else{
                dataMap.put("code",0);
                dataMap.put("message","用户已存在！");
                jsonObject = new JSONObject(dataMap);
                return SUCCESS;
            }
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
    }


    /**
     * @description: 登录Action
     * @param:  NONE
     * @return: java.lang.String
     * @author nanyan
     * @date:  21:23
     */
    @Action(value = "userLogin", results = {
            @Result(type = "json",params = {"root","jsonObject"})
    })
    public String userLogin(){
        try {
            //获取User数量，并在登录时就执行setAttribute
            session.setAttribute("userNumber",userService.getUserNumber());
            String sessionVcode = (String) session.getAttribute("session_vcode");
            System.out.println(user);

            //调试用，优化时删除
//        dataMap.put("username",userName);
//        dataMap.put("password",password);
//        dataMap.put("Vcode",sessionVcode);


            if(!sessionVcode.equalsIgnoreCase(captcha.trim())){
                dataMap.put("code",0);
                dataMap.put("message","验证码错误！");
                jsonObject = new JSONObject(dataMap);
                return SUCCESS;
            }

            User tmpUser = userService.findByUserName(userName);
            //用户不存在
            if(tmpUser == null){
                dataMap.put("code",0);
                dataMap.put("message","用户不存在或已删除！");
                jsonObject = new JSONObject(dataMap);
                return SUCCESS;
            }
            //用户已被禁用
            if(tmpUser.getStatus() == 0){
                dataMap.put("code",0);
                dataMap.put("message","该账户已被禁用，请联系管理员！");
                jsonObject = new JSONObject(dataMap);
                return SUCCESS;
            }
            //登录成功
            if(tmpUser.getPassword().equals(password)){
                session.setAttribute("user",tmpUser);
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
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
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
        session.removeAttribute("user");
        dataMap.put("code",1);
        dataMap.put("message","登出成功！");
        jsonObject = new  JSONObject(dataMap);
        return SUCCESS;
    }

    /**
     * @description: 获取用户列表接口
     * @param:  NONE
     * @return: java.lang.String
     * @author nanyan
     * @date:  21:25
     */
    @Action(value = "getUserListByPage",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String getUserList(){
        try {
            //获取User数量
            int userNumber = userService.getUserNumber();
            //获取User列表
            List<User> userList = userService.getUserListByPage(page,limit);

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < userList.size(); i++) {
                tmpMap.put(String.valueOf(i),JSON.toJSON(userList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",userNumber);
            dataMap.put("data",tmpMap);
//        System.out.println(dataMap);
            jsonObject = new  JSONObject(dataMap);
            return SUCCESS;
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
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


    @Action(value = "addUser",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String addUser(){
        try {
            User orgUser = userService.findByUserName(userName);
            System.out.println("存在的用户："+orgUser);

            if(orgUser == null){
                User tmpUser = new User();
                tmpUser.setUserName(userName);
                tmpUser.setPassword(password);
                tmpUser.setPhoneNumber(phoneNumber);
                tmpUser.setIsAdmin(isAdmin);
                tmpUser.setSex(sex);

                System.out.println("要添加的用户："+tmpUser);
                userService.addUser(tmpUser);
                //添加完成后刷新数量
                session.setAttribute("userNumber",userService.getUserNumber());

                dataMap.put("code",1);
                dataMap.put("message","添加成功！");
                jsonObject = new JSONObject(dataMap);
            }
            else {
                dataMap.put("code",0);
                dataMap.put("message","用户名已存在！");
                jsonObject = new JSONObject(dataMap);
            }
            return SUCCESS;
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
    }

    @Action(value = "findByUserName",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String findByUserName(){
        try {
            User tmpUser = userService.findByUserName(userName);
            dataMap.put("code",0);
            dataMap.put("message","查找成功！");
            dataMap.put("data",tmpUser);
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
    }

    @Action(value = "findListByUserName",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String findListByUserName(){
        try {
            List<User> list = userService.findListByUserName(userName);

//        JSONObject data = new JSONObject();
            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < list.size(); i++) {
                tmpMap.put(String.valueOf(i),JSON.toJSON(list.get(i),serializeConfig));
    //            data.put(String.valueOf(i),JSON.toJSON(list.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",list.size());
            dataMap.put("data",tmpMap);
//        System.out.println(dataMap);
            jsonObject = new  JSONObject(dataMap);
//        System.out.println("查找后："+jsonObject);
            return SUCCESS;
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
    }

    @Action(value = "changeUserStatus",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String changeUserStatus(){
        try {
            userService.changeUserStatus(id,status);
            dataMap.put("message","操作成功！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
    }

    @Action(value = "deleteById",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String deleteById(){
        try {
            System.out.println("要删除的用户ID: "+id);
            userService.deleteById(id);

            //删除后刷新数量
            session.setAttribute("userNumber",userService.getUserNumber());

            dataMap.put("code",1);
            dataMap.put("message","删除成功！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
    }

    @Action(value = "editUser",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String editUser(){

        try {
            User orgUser = userService.findByUserName(userName);
//            System.out.println("存在的用户："+orgUser);

            if(orgUser == null){
                User tmpUser = new User();
                tmpUser.setId(id);
                tmpUser.setUserName(userName);
                tmpUser.setPassword(password);
                tmpUser.setPhoneNumber(phoneNumber);
                tmpUser.setIsAdmin(isAdmin);
                tmpUser.setSex(sex);

                System.out.println(tmpUser);
                userService.editUser(id,tmpUser);
                dataMap.put("code",1);
                dataMap.put("message","修改成功！");
                jsonObject = new JSONObject(dataMap);
            }
            else {
                dataMap.put("code",0);
                dataMap.put("message","用户名已存在！");
                jsonObject = new JSONObject(dataMap);
            }
            return SUCCESS;
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
    }



    @Action(value = "changeInfoByUsername",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String changeInfoByUsername(){

        try {
            if(userService.findByUserName(userName) != null){
                dataMap.put("code",0);
                dataMap.put("message","用户名已存在，试试别的吧！");
                jsonObject = new JSONObject(dataMap);
                return SUCCESS;
            }
            String curUsername = ((User)session.getAttribute("user")).getUserName();
            User tmpUser = new User();
            tmpUser.setUserName(userName);
            tmpUser.setPhoneNumber(phoneNumber);
            tmpUser.setSex(sex);
            userService.changeInfoByUsername(curUsername,tmpUser);

            //刷新存在的User
            session.removeAttribute("user");
            session.setAttribute("user",userService.findByUserName(userName));

            dataMap.put("code",1);
            dataMap.put("message","修改成功！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
    }

    @Action(value = "changePwdByUsername",
            results = {@Result(type = "json",params = {"root","jsonObject"})},
            interceptorRefs = {@InterceptorRef(value = "LoginInterceptorStack")}
    )
    public String changePwdByUsername(){
        try {
            User tmpUser = (User) session.getAttribute("user");

            //原密码错误
            if(!old_password.equals(tmpUser.getPassword())){
                dataMap.put("code",0);
                dataMap.put("message","原密码错误，请重试！");
                jsonObject = new JSONObject(dataMap);
                return SUCCESS;
            }

            userService.changePwdByUsername(tmpUser.getUserName(), new_password);
            dataMap.put("code",1);
            dataMap.put("message","操作成功！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;

        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            jsonObject = new JSONObject(dataMap);
            return SUCCESS;
        }
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

    public User getUser(){
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


}
