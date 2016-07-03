package com.iot.baobiao.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by ja on 2016/6/22.
 */
public class BaobiaoWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //指定ContextLoaderListener加载应用上下文时使用的配置类
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class};
    }

    //指定DispatcherServlet加载应用上下文时使用的配置类
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class};
    }

    //把DispatcherServlet映射到"/"
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
