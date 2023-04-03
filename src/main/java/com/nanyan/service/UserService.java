package com.nanyan.service;

import com.alibaba.fastjson.JSONObject;

public interface UserService {

    public JSONObject userRegister(String userName,String password,String sex,String phoneNumber,String captcha);
    public JSONObject userLogin(String userName,String password,String captcha);
    public JSONObject userLogOut(String username);
    public JSONObject getUserList();
    public JSONObject getUserListByPage(int page,int limit);
    public JSONObject addUser(String userName,String password,String phoneNumber,int isAdmin,String sex);
    public JSONObject findByUserName(String userName);
    public JSONObject getUserListByUserName(String userName,int page,int limit);
    public JSONObject changeUserStatus(int id, int status);
    public JSONObject deleteById(int id);
    public JSONObject editUser(String userName,int id,String password,String phoneNumber,int isAdmin,String sex);
    public JSONObject changeInfoByUsername(String userName,String phoneNumber,String sex);
    public JSONObject changePwdByUsername(String new_password,String old_password);

}
