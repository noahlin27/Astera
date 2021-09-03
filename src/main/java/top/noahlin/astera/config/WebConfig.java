package top.noahlin.astera.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.noahlin.astera.util.interceptor.PassportInterceptor;

import javax.annotation.Resource;

public class WebConfig implements WebMvcConfigurer {
    @Resource
    PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
