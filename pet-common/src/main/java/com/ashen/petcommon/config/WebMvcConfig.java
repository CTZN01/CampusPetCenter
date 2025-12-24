package com.ashen.petcommon.config;

import com.ashen.petcommon.interceptor.JwtAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtAuthInterceptor jwtAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/**")              // 拦截所有请求
                .excludePathPatterns(
                        "/users/login/**",                 // 登录接口
                        "/swagger-ui/**",             // Swagger UI
                        "/v3/api-docs/**",            // OpenAPI 文档
                        "/error",
                        "/users/register"               // 注册接口
                );
    }
}
