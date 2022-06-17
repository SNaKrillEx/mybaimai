package com.shop.service;

import com.shop.pojo.User;

import java.util.List;

public interface UserService {

    List<User> isLogin(String name, String password);

    public void regesiterUser(User user);
}
