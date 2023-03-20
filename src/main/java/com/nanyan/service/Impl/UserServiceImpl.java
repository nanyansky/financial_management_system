package com.nanyan.service.Impl;

import com.nanyan.dao.UserDao;
import com.nanyan.entity.User;
import com.nanyan.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public User getUser(int id) {
        User user = userDao.getUser(id);
        return user;
    }

    @Override
    public User findByUserName(String username) {
        User user = userDao.findByUserName(username);
        return user;
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

}
