package com.iot.baobiao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by ja on 2016/6/22.
 */

@Configuration
@EnableWebMvc      //启用SpringMVC
//启用组件扫描
@ComponentScan(basePackages = "com.iot.baobiao.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

    //配置静态资源的处理
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //设置后DispatcherServlet将对静态资源的请求转发到Servlet容器中默认的Servlet上，
        // 而不是使用DispatcherServlet本身来处理此类请求
        configurer.enable();
    }
}
