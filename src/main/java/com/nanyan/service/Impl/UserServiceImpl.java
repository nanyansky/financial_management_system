package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.dao.UserDao;
import com.nanyan.entity.User;
import com.nanyan.service.UserService;
import com.nanyan.utils.OperationType;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private Map<String, Object> dataMap = new HashMap<String, Object>();

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }


    @Resource
    UserDao userDao;


    @Override
    public List<User> getUserListByPage(int currentPage,int perPageRows) {
        return userDao.getUserListByPage(currentPage,perPageRows);
    }

    @Override
    public int getUserNumber() {
        return userDao.getUserNumber();
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public User findByUserId(int id) {
        return userDao.findByUserId(id);
    }


    @Override
    @OptLog(content = "用户注册",operationType = OperationType.REGISTER)
    public JSONObject userRegister(String userName,String password,String sex,String phoneNumber,String captcha) {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            String sessionVcode = (String) session.getAttribute("session_vcode");
            if(!sessionVcode.equalsIgnoreCase(captcha.trim())){
                dataMap.put("code",0);
                dataMap.put("message","验证码错误！");
                JSONObject jsonObject = new JSONObject(dataMap);
                return jsonObject;
            }
            //查找是否存在同名用户
            User tmpUser = userDao.findByUserName(userName);
            if(tmpUser == null){
                User newUser = new User();
                newUser.setUserName(userName);
                newUser.setPassword(password);
                newUser.setSex(sex);
                newUser.setPhoneNumber(phoneNumber);
                userDao.addUser(newUser);

                dataMap.put("code",1);
                dataMap.put("message","注册成功！请等待管理员审核！");
                JSONObject jsonObject = new JSONObject(dataMap);
                return jsonObject;
            }
            else{
                dataMap.put("code",0);
                dataMap.put("message","用户已存在！");
                JSONObject jsonObject = new JSONObject(dataMap);
                return jsonObject;
            }
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            JSONObject jsonObject = new JSONObject(dataMap);
            return jsonObject;
        }
    }

    @Override
    @OptLog(content = "用户登录",operationType = OperationType.LOGIN)
    public JSONObject userLogin(String userName,String password,String captcha) {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            //获取User数量，并在登录时就执行setAttribute
            session.setAttribute("userNumber",userDao.getUserNumber());
            String sessionVcode = (String) session.getAttribute("session_vcode");

            //调试用，优化时删除
//        dataMap.put("username",userName);
//        dataMap.put("password",password);
//        dataMap.put("Vcode",sessionVcode);


            if(!sessionVcode.equalsIgnoreCase(captcha.trim())){
                dataMap.put("code",0);
                dataMap.put("message","验证码错误！");
                JSONObject jsonObject = new JSONObject(dataMap);
                return jsonObject;
            }

            User tmpUser = userDao.findByUserName(userName);
            //用户不存在
            if(tmpUser == null){
                dataMap.put("code",0);
                dataMap.put("message","用户不存在或已删除！");
                JSONObject jsonObject = new JSONObject(dataMap);
                return jsonObject;
            }
            //用户已被禁用
            if(tmpUser.getStatus() == 0){
                dataMap.put("code",0);
                dataMap.put("message","该账户已被禁用，请联系管理员！");
                JSONObject jsonObject = new JSONObject(dataMap);
                return jsonObject;
            }
            //登录成功
            if(tmpUser.getPassword().equals(password)){
                session.setAttribute("user",tmpUser);
                dataMap.put("code",1);
                dataMap.put("message","登录成功！");
                JSONObject jsonObject = new JSONObject(dataMap);
                System.out.println(jsonObject);
                return jsonObject;
            }
            else {
                dataMap.put("code",0);
                dataMap.put("message","密码错误！");
                JSONObject jsonObject = new JSONObject(dataMap);
                System.out.println(jsonObject);
                return jsonObject;
            }
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            JSONObject jsonObject = new JSONObject(dataMap);
            return jsonObject;
        }
    }

    @Override
    @OptLog(content = "用户登出", operationType = OperationType.LOGOUT)
    public JSONObject userLogOut(String username) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("user");
        dataMap.put("code",1);
        dataMap.put("message","登出成功！");
        return new JSONObject(dataMap);
    }

    @Override
    public JSONObject getUserList(int page,int limit) {
        try {
            //获取User数量
            int userNumber = userDao.getUserNumber();
            //获取User列表
            List<User> userList = userDao.getUserListByPage(page,limit);

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < userList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(userList.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            dataMap.put("count",userNumber);
            dataMap.put("data",tmpMap);
//            System.out.println(dataMap);
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "添加用户", operationType = OperationType.INSERT)
    public JSONObject addUser(String userName,String password,String phoneNumber,int isAdmin,String sex) {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            User orgUser = userDao.findByUserName(userName);
            System.out.println("存在的用户："+orgUser);

            if(orgUser == null){
                User tmpUser = new User();
                tmpUser.setUserName(userName);
                tmpUser.setPassword(password);
                tmpUser.setPhoneNumber(phoneNumber);
                tmpUser.setIsAdmin(isAdmin);
                tmpUser.setSex(sex);

                System.out.println("要添加的用户："+tmpUser);
                userDao.addUser(tmpUser);
                //添加完成后刷新数量
                session.setAttribute("userNumber",userDao.getUserNumber());

                dataMap.put("code",1);
                dataMap.put("message","添加成功！");
                return new JSONObject(dataMap);
            }
            else {
                dataMap.put("code",0);
                dataMap.put("message","用户名已存在！");
                return new JSONObject(dataMap);
            }
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "查找用户", operationType = OperationType.SELECT)
    public JSONObject findByUserName(String userName) {
        try {
            User tmpUser = userDao.findByUserName(userName);
            dataMap.put("code",0);
            dataMap.put("message","查找成功！");
            dataMap.put("data",tmpUser);
            return new JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "查找用户", operationType = OperationType.SELECT)
    public JSONObject findListByUserName(String userName,int page,int limit) {
        try {
            List<User> list = userDao.findListByUserName(userName,page,limit);

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
            return new  JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "修改用户信息", operationType = OperationType.UPDATE)
    public JSONObject changeUserStatus(int id, int status) {
        try {
            userDao.changeUserStatus(id,status);
            dataMap.put("message","操作成功！");
            return new JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "修改用户信息", operationType = OperationType.UPDATE)
    public JSONObject deleteById(int id) {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            System.out.println("要删除的用户ID: "+id);
            userDao.deleteById(id);

            //删除后刷新数量
            session.setAttribute("userNumber",userDao.getUserNumber());

            dataMap.put("code",1);
            dataMap.put("message","删除成功！");
            return new JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "编辑用户资料", operationType = OperationType.UPDATE)
    public JSONObject editUser(String userName,int id,String password,String phoneNumber,int isAdmin,String sex) {
        try {
            User orgUser = userDao.findByUserName(userName);
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
                userDao.editUser(id,tmpUser);
                dataMap.put("code",1);
                dataMap.put("message","修改成功！");
                return new JSONObject(dataMap);
            }
            else {
                dataMap.put("code",0);
                dataMap.put("message","用户名已存在！");
                return new JSONObject(dataMap);
            }
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "修改用户信息", operationType = OperationType.UPDATE)
    public JSONObject changeInfoByUsername(String userName,String phoneNumber,String sex) {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            if(userDao.findByUserName(userName) != null){
                dataMap.put("code",0);
                dataMap.put("message","用户名已存在，试试别的吧！");
                return new JSONObject(dataMap);
            }
            String curUsername = ((User)session.getAttribute("user")).getUserName();
            User tmpUser = new User();
            tmpUser.setUserName(userName);
            tmpUser.setPhoneNumber(phoneNumber);
            tmpUser.setSex(sex);
            userDao.changeInfoByUsername(curUsername,tmpUser);

            //刷新存在的User
            session.removeAttribute("user");
            session.setAttribute("user",userDao.findByUserName(userName));

            dataMap.put("code",1);
            dataMap.put("message","修改成功！");
            return new JSONObject(dataMap);
        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }

    @Override
    @OptLog(content = "修改用户信息", operationType = OperationType.UPDATE)
    public JSONObject changePwdByUsername(String new_password,String old_password) {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            User tmpUser = (User) session.getAttribute("user");

            //原密码错误
            if(!old_password.equals(tmpUser.getPassword())){
                dataMap.put("code",0);
                dataMap.put("message","原密码错误，请重试！");
                return new JSONObject(dataMap);
            }

            userDao.changePwdByUsername(tmpUser.getUserName(), new_password);
            dataMap.put("code",1);
            dataMap.put("message","操作成功！");
            return new JSONObject(dataMap);

        } catch (Exception e) {
            dataMap.put("code",0);
            dataMap.put("message","服务器错误，请重试！");
            return new JSONObject(dataMap);
        }
    }


    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
