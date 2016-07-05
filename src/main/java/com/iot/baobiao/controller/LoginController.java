package com.iot.baobiao.controller;

import com.iot.baobiao.pojo.User;
import com.iot.baobiao.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ja on 2016/6/22.
 */

//负责登录的请求
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestParam String phonenum, @RequestParam String password) {
        System.out.println("/login");
        User user = new User(phonenum, password);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "ok");
        map.put("user", loginService.isMatch(user));
        return map;
    }
}
