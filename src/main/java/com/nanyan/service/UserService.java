package com.nanyan.service;

import com.nanyan.entity.User;

import java.util.List;

public interface UserService {

//    public User getUser(int id);

    public User findByUserName(String username);

//    void save(User user);

    public List<User> getUserListByPage(int currentPage,int perPageRows);


    public int getUserNumber();

    public void addUser(User user);

    public User findByUserId(int id);

    public List<User> findListByUserName(String username);

    public void changeUserStatus(int id, int status);
    public void deleteById(int id);

    public void editUser(int id,User user);
}
