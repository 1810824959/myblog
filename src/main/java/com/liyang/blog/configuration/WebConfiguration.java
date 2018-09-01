package com.liyang.blog.configuration;

//import com.liyang.blog.interceptor.ArticleClickInterceptor;
import com.liyang.blog.interceptor.LoginRequestInterceptor;
import com.liyang.blog.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tuzhenyu on 17-7-20.
 * @author tuzhenyu
 */
@Component
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequestInterceptor loginRequestInterceptor;

//    @Autowired
//    private ArticleClickInterceptor articleClickInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequestInterceptor).addPathPatterns(new ArrayList<String>(){{add("/"); add("/index");}});
//        registry.addInterceptor(articleClickInterceptor).addPathPatterns("/article/*");
        super.addInterceptors(registry);
    }
}
