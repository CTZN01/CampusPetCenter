package com.ashen.petcommon.interceptor;

import com.ashen.petcommon.context.UserContext;
import com.ashen.petcommon.model.LoginUser;
import com.ashen.petcommon.properties.GlobalInterceptorProperties;
import com.ashen.petcommon.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 登录认证拦截器
 */
@Component
@Slf4j
public class JwtAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private GlobalInterceptorProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        log.info("请求URI: {} | 请求IP: {}", requestURI, getRequestIp(request));
        //1.跳过无需校验的接口(登录接口等)
        Set<String> excludePaths = properties.getExcludePaths();
        if(properties.getExcludePaths().stream().anyMatch(requestURI::startsWith)){
            return true;
        }
        String token = request.getHeader("Authorization");
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            if(StringUtils.isBlank(token) || !token.startsWith("Bearer ")){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("用户未登录或登录已过期，请重新登录");
                return false;
            }
            token = token.substring(7);
            if(!JwtUtils.validateToken(token)){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("用户未登录或登录已过期，请重新登录");
                return false;
            }
            Claims claims = JwtUtils.parseToken(token);
            LoginUser loginUser = new LoginUser();
            loginUser.setUsername(claims.getSubject());
            loginUser.setUserId(claims.get("userId", Long.class));
            @SuppressWarnings("unchecked")
            List<String> roleList = claims.get("roles", List.class);
            Set<String> roles = new HashSet<>(roleList);
            loginUser.setRoles(roles);
            UserContext.set(loginUser);

            return true;
        } catch (Exception e) {
            log.error("JWT认证失败: ", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().write("JWT认证失败: " + e.getMessage());
            } catch (IOException ioException) {
                log.error("响应输出流异常", ioException);
            }
            return false;
        }
    }

    /**
     * 获取请求的真实IP地址
     * @param request
     * @return
     */
    private String getRequestIp(HttpServletRequest request){
        String ip = request.getHeader("X-Real-IP");
        if(StringUtils.isBlank(ip)){
            ip = request.getHeader("X-Forwarded-For");
        }
        if(StringUtils.isBlank(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.contains(",") ? ip.split(",")[0].trim() : ip;
    }
}
