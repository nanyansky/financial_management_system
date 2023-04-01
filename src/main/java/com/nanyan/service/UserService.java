package com.nanyan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanyan.annotation.OptLog;
import com.nanyan.entity.User;
import com.nanyan.utils.OperationType;

import java.util.List;

public interface UserService {

//    public User getUser(int id);


//    void save(User user);

    public List<User> getUserListByPage(int currentPage,int perPageRows);


    public int getUserNumber();

    public void addUser(User user);

    public User findByUserId(int id);






    public JSONObject userRegister(String userName,String password,String sex,String phoneNumber,String captcha);

    public JSONObject userLogin(String userName,String password,String captcha);

    public JSONObject userLogOut(String username);

    public JSONObject getUserList(int page,int limit);
    public JSONObject addUser(String userName,String password,String phoneNumber,int isAdmin,String sex);
    public JSONObject findByUserName(String userName);
    public JSONObject findListByUserName(String userName,int page,int limit);
    public JSONObject changeUserStatus(int id, int status);
    public JSONObject deleteById(int id);
    public JSONObject editUser(String userName,int id,String password,String phoneNumber,int isAdmin,String sex);
    public JSONObject changeInfoByUsername(String userName,String phoneNumber,String sex);
    public JSONObject changePwdByUsername(String new_password,String old_password);

}
