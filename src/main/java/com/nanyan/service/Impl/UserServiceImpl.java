package com.nanyan.service.Impl;

import com.nanyan.dao.UserDao;
import com.nanyan.entity.User;
import com.nanyan.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service

public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public User findByUserName(String username) {
        User user = userDao.findByUserName(username);
        return user;
    }


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
    public List<User> findListByUserName(String username) {
        return userDao.findListByUserName(username);

    }

    @Override
    public void changeUserStatus(int id, int status) {
        userDao.changeUserStatus(id,status);
    }


    @Override
    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    @Override
    public void editUser(int id,User user) {
        userDao.editUser(id,user);
    }


}
