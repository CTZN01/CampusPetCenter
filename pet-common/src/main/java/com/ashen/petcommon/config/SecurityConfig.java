package com.ashen.petcommon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. 禁用 CSRF（前后端分离项目通常不需要，且会阻碍 POST 请求）
            .csrf(AbstractHttpConfigurer::disable)

            // 2. 禁用 Session（使用 JWT，不需要服务端 Session）
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 3. 禁用默认的表单登录（防止弹出默认登录页）
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            // 4. 配置路径放行规则
            .authorizeHttpRequests(auth -> auth
                // 虽然你在 Interceptor 里放行了，但必须先过 Security 这一关
                // 放行 Swagger 相关
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                // 放行 登录、注册
                .requestMatchers("/users/login/**", "/users/register").permitAll()
                // 放行 错误页面
                .requestMatchers("/error").permitAll()

                // ★★★ 关键点 ★★★
                // 允许所有其他请求通过 Spring Security 的过滤器链。
                // 只有通过了这里，请求才能到达你的 Controller，从而被 WebMvcConfig 中的 Interceptor 拦截校验。
                // 如果这里写 .authenticated()，你的 Interceptor 将永远无法执行 Token 校验。
                .anyRequest().permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}