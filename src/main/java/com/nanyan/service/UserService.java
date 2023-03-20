package com.nanyan.service;

import com.nanyan.entity.User;

public interface UserService {
    public User getUser(int id);

    public User findByUserName(String username);

    void save(User user);
}
