package com.web.wps.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class UserMvcAdapter extends WebMvcConfigurerAdapter {

    private static String[] URL_PATTERNS = new String[] { "/v1/**"};

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserHandlerAdapter()).addPathPatterns(URL_PATTERNS);
        super.addInterceptors(registry);
    }

}
