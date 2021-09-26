package top.noahlin.astera.config;

import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.noahlin.astera.interceptor.LoginRequiredInterceptor;
import top.noahlin.astera.interceptor.PassportInterceptor;

import javax.annotation.Resource;

@Component
public class WebConfig implements WebMvcConfigurer {
    @Resource
    PassportInterceptor passportInterceptor;

    @Resource
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
