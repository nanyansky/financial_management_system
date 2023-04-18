package com.nanyan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.annotation.OptLog;
import com.nanyan.dao.ExpenseDao;
import com.nanyan.dao.IncomeDao;
import com.nanyan.dao.UserDao;
import com.nanyan.entity.User;
import com.nanyan.service.UserService;
import com.nanyan.utils.MailUtil;
import com.nanyan.utils.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {


    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    UserDao userDao;
    @Resource
    IncomeDao incomeDao;
    @Resource
    ExpenseDao expenseDao;

    @Override
    @OptLog(content = "用户注册",operationType = OperationType.REGISTER)
    public JSONObject userRegister(String userName,String password,String email,String sex,String phoneNumber,String captcha) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
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
                newUser.setEmail(email);
                newUser.setSex(sex);
                newUser.setPhoneNumber(phoneNumber);
                userDao.addUser(newUser);

                List<User> allAdmin = userDao.getAllAdmin();
                List<String> mailList = new ArrayList<>();
                for (User user: allAdmin){
                    mailList.add(user.getEmail());
                }
                //通知管理员审核账号
                MailUtil.sendMail(mailList,userName);

                //删除缓存
                stringRedisTemplate.delete("userCacheEntitySet");
                stringRedisTemplate.delete("userCacheIdSet");
                stringRedisTemplate.delete("userNum");

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
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            //获取User、income、expense数量，并在登录时就执行setAttribute
            session.setAttribute("userNumber",userDao.getUserNumber());
            session.setAttribute("incomeNumber",incomeDao.getIncomeNumber());
            session.setAttribute("expenseNumber",expenseDao.getExpenseNumber());
            session.setAttribute("expenseCount",expenseDao.getExpenseCount());
            session.setAttribute("incomeCount",incomeDao.getIncomeCount());
            String sessionVcode = (String) session.getAttribute("session_vcode");

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
        Map<String, Object> dataMap = new HashMap<String, Object>();
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("user");
        dataMap.put("code",1);
        dataMap.put("message","登出成功！");
        return new JSONObject(dataMap);
    }

    @Override
    public JSONObject getUserList() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //获取User数量
            int userNumber = userDao.getUserNumber();
            //获取User列表
            List<User> userList = userDao.getUserList();

            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < userList.size(); i++) {
                tmpMap.put(String.valueOf(i), JSON.toJSON(userList.get(i),serializeConfig));
            }

            System.out.println(dataMap);
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
    public JSONObject getUserListByPage(int page,int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            String IdKey = "userCacheIdSet";
            String EntityKey = "userCacheEntitySet";
            String NumKey = "userNum";
            int userNumber;
            List<User> userList = new ArrayList<>();
            int start = (page-1)*limit + 1;
            int end = page*limit;
            //查找缓存
            String userNum = stringRedisTemplate.opsForValue().get(NumKey);
            Set<String> userIDSet = stringRedisTemplate.opsForZSet().rangeByScore(IdKey, start, end);

            System.out.println(userIDSet);

            Map<String,Object> tmpMap = new HashMap<>();
            if(userNum != null){
//                System.out.println("名字");
                int i = 0;
                userNumber = Integer.parseInt(userNum);
                for (String s : userIDSet) {
                    String o = (String) stringRedisTemplate.opsForHash().get(EntityKey, s);
                    tmpMap.put(String.valueOf(i++),JSONObject.parseObject(o));
//                    System.out.println(o);
                }
            }
            else {
                userNumber = userDao.getUserNumber();
                stringRedisTemplate.opsForValue().set(NumKey,String.valueOf(userNumber));

                userList = userDao.getUserList();
                int i =0;
                for (User userTmp : userList) {
                    tmpMap.put(String.valueOf(i++), JSON.toJSON(userTmp,serializeConfig));
                    stringRedisTemplate.opsForZSet().add(IdKey, String.valueOf(userTmp.getId()), i);
                    stringRedisTemplate.opsForHash().put(EntityKey,String.valueOf(userTmp.getId()),JSONObject.toJSONString(userTmp,serializeConfig));
                }
            }

//
//            //获取User数量
//            userNumber = userDao.getUserNumber();
//            //获取User列表
//            userList = userDao.getUserListByPage(page,limit);
//
//
//
//            for (int i = 0; i < userList.size(); i++) {
//                tmpMap.put(String.valueOf(i), JSON.toJSON(userList.get(i),serializeConfig));
//            }

//            System.out.println(dataMap);


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
    public JSONObject addUser(String userName,String password,String email,String phoneNumber,int isAdmin,String sex) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            User orgUser = userDao.findByUserName(userName);
            System.out.println("存在的用户："+orgUser);

            if(orgUser == null){
                User tmpUser = new User();
                tmpUser.setUserName(userName);
                tmpUser.setPassword(password);
                tmpUser.setEmail(email);
                tmpUser.setPhoneNumber(phoneNumber);
                tmpUser.setIsAdmin(isAdmin);
                tmpUser.setSex(sex);

                System.out.println("要添加的用户："+tmpUser);
                userDao.addUser(tmpUser);
                //添加完成后刷新数量
                session.setAttribute("userNumber",userDao.getUserNumber());

                //删除缓存
                stringRedisTemplate.delete("userCacheEntitySet");
                stringRedisTemplate.delete("userCacheIdSet");
                stringRedisTemplate.delete("userNum");

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
        Map<String, Object> dataMap = new HashMap<String, Object>();
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
    public JSONObject getUserListByUserName(String userName,int page,int limit) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<User> list = userDao.getUserListByUserName(userName,page,limit);

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
    @OptLog(content = "搜索用户", operationType = OperationType.SELECT)
    public JSONObject searchUser(String userName, int isAdmin, int status,int page, int limit) {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<User> list = userDao.searchUser(userName,isAdmin,status,page,limit);

//        JSONObject data = new JSONObject();
            Map<String,Object> tmpMap = new HashMap<>();

            for (int i = 0; i < list.size(); i++) {
                tmpMap.put(String.valueOf(i),JSON.toJSON(list.get(i),serializeConfig));
                //            data.put(String.valueOf(i),JSON.toJSON(list.get(i),serializeConfig));
            }

            dataMap.put("code",0);
            System.out.println(userDao.searchUserNumber(userName,isAdmin,status));
            dataMap.put("count",userDao.searchUserNumber(userName,isAdmin,status));
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
    @OptLog(content = "修改用户状态", operationType = OperationType.UPDATE)
    public JSONObject changeUserStatus(int id, int status) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            //删除缓存
            stringRedisTemplate.delete("userCacheEntitySet");
            stringRedisTemplate.delete("userCacheIdSet");
            stringRedisTemplate.delete("userNum");

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
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            System.out.println("要删除的用户ID: "+id);

            //删除缓存
            stringRedisTemplate.delete("userCacheEntitySet");
            stringRedisTemplate.delete("userCacheIdSet");
            stringRedisTemplate.delete("userNum");

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
    public JSONObject editUser(String userName,int id,String password,String email,String phoneNumber,int isAdmin,String sex) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            User orgUser = userDao.findByUserName(userName);
//            System.out.println("存在的用户："+orgUser);

            //新改的用户名不存在或没改用户名
            if(orgUser == null || orgUser.getId() == id){
                User tmpUser = new User();
                tmpUser.setId(id);
                tmpUser.setUserName(userName);
                tmpUser.setPassword(password);
                tmpUser.setEmail(email);
                tmpUser.setPhoneNumber(phoneNumber);
                tmpUser.setIsAdmin(isAdmin);
                tmpUser.setSex(sex);

                //删除缓存
                stringRedisTemplate.delete("userCacheEntitySet");
                stringRedisTemplate.delete("userCacheIdSet");
                stringRedisTemplate.delete("userNum");

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
        Map<String, Object> dataMap = new HashMap<String, Object>();
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

            //删除缓存
            stringRedisTemplate.delete("userCacheEntitySet");
            stringRedisTemplate.delete("userCacheIdSet");
            stringRedisTemplate.delete("userNum");

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
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            User tmpUser = (User) session.getAttribute("user");

            //原密码错误
            if(!old_password.equals(tmpUser.getPassword())){
                dataMap.put("code",0);
                dataMap.put("message","原密码错误，请重试！");
                return new JSONObject(dataMap);
            }

            //删除缓存
            stringRedisTemplate.delete("userCacheEntitySet");
            stringRedisTemplate.delete("userCacheIdSet");
            stringRedisTemplate.delete("userNum");

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

}
