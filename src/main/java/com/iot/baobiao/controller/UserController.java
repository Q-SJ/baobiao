package com.iot.baobiao.controller;

import com.iot.baobiao.pojo.User;
import com.iot.baobiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ja on 2016/6/30.
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/info")
    public Map<String, Object> updateUserInfo(@RequestParam int user_id, @RequestParam(required = false) String username,
                                              @RequestParam(required = false) String email, @RequestParam(required = false) String corporation ,
                                              @RequestParam(required = false) int industry) {
        User user = new User(user_id, username, email, corporation, industry);
        Map<String, Object> map = new HashMap<String, Object>();
        userService.updateUserInfo(user);
        map.put("status", "ok");
        return map;
    }

    //修改用户的关键字
    @RequestMapping("/keyword")
    public Map<String, Object> modifyKeyword(@RequestParam int user_id, @RequestParam String keyword) {
        User user = new User(user_id, keyword);
        userService.modifyKeyword(user);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "ok");
        return map;
    }

    @RequestMapping("/getKeywords")
    public Map<String, String> keyword(@RequestParam int user_id) {
        Map<String, String> map =  new HashMap<String, String>();
        map.put("keywords", userService.keyword(user_id));
        return map;
    }

    @RequestMapping("/test1")
    public Map<String, Object> test() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "ok");
        return map;
    }
}
