package com.iot.baobiao.controller;

import com.iot.baobiao.exception.DuplicateSite;
import com.iot.baobiao.exception.NoDataException;
import com.iot.baobiao.exception.SiteNotFoundException;
import com.iot.baobiao.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ja on 2016/6/22.
 */

@ControllerAdvice
@RestController
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> emptyUserHandler() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "error");
        map.put("message", "用户名或密码错误！");
        return map;
    }

    @ExceptionHandler(SiteNotFoundException.class)
    public Map<String, String> emptySiteHandler() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "error");
        map.put("message", "暂无自选网站！");
        return map;
    }

    @ExceptionHandler(DuplicateSite.class)
    public Map<String, String> duplicateSite() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "error");
        map.put("message", "您已添加过此网站！");
        return map;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> emptyDataHandler() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "error");
        map.put("message", "暂无抓取数据！");
        return map;
    }
}
