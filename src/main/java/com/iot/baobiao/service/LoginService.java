package com.iot.baobiao.service;

import com.iot.baobiao.dao.UserDao;
import com.iot.baobiao.exception.UserNotFoundException;
import com.iot.baobiao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * Created by ja on 2016/6/22.
 */

@Service
public class LoginService {

    @Autowired
    private UserDao userDao;

    //检验用户传递的用户名和密码是否匹配
    public User isMatch(User user) {
        //按照用户输入的用户名查询数据库中对应的用户,如果没有找到用户，抛出异常，如果找到的用户的密码不符，也抛出异常
        try {
            User dbUser = userDao.findUserByPhonenum(user.getPhonenum());
            if (!dbUser.getPassword().equals(user.getPassword())) {
                throw new UserNotFoundException();
            }
            return dbUser;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();
        }

    }
}
