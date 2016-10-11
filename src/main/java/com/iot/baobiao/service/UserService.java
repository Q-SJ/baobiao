package com.iot.baobiao.service;

import com.iot.baobiao.dao.UserDao;
import com.iot.baobiao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ja on 2016/6/30.
 */

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public void updateUserInfo(User user) {
        userDao.updateInfo(user);
    }

    public void modifyKeyword(User user) {
        userDao.modifyKeyword(user);
    }

    public String keyword(int user_id) {return userDao.getKeyword(user_id);}
}
