package com.shop.service;

import com.shop.mapper.UserMapper;
import com.shop.pojo.User;
import com.shop.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp  implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> isLogin(String username, String password) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        //数据库查询用户登录     select * from userName=? and userPassword=?;
        criteria.andUsernameEqualTo(username);
        criteria.andUserpasswordEqualTo(password);

        List<User> users = userMapper.selectByExample(userExample);

        System.out.println(users);

        return users;
    }

    @Override
    public void regesiterUser(User user) {
        userMapper.insert(user);
    }
}
