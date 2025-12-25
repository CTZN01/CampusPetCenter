package com.ashen.petcommon.aspect;

import com.ashen.petcommon.annotation.RequireRoles;
import com.ashen.petcommon.context.UserContext;
import com.ashen.petcommon.model.BaseException;
import com.ashen.petcommon.model.LoginUser;
import com.ashen.petcommon.model.enums.HttpStatusEnum;
import com.ashen.petcommon.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * 权限校验切面
 */
@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Around("@annotation(com.ashen.petcommon.annotation.RequireRoles)")
    public Object checkRoles(ProceedingJoinPoint point) throws Throwable {
        // 1. 获取注解信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RequireRoles requireRoles = method.getAnnotation(RequireRoles.class);

        String[] requiredRoles = requireRoles.value();
        if (requiredRoles == null || requiredRoles.length == 0) {
            return point.proceed();
        }

        // 2. 获取当前登录用户
        LoginUser loginUser = UserUtils.getCurrentUser();
        if (loginUser == null) {
            throw new BaseException(HttpStatusEnum.UNAUTHORIZED.getCode(), "用户未登录");
        }

        // 3. 校验角色
        Set<String> userRoles = loginUser.getRoles();
        boolean hasRole = false;

        // 只要拥有注解中定义的任意一个角色，即视为有权限
        for (String role : requiredRoles) {
            if (userRoles.contains(role)) {
                hasRole = true;
                break;
            }
        }

        if (!hasRole) {
            log.warn("用户 [{}] 尝试访问 [{}] 接口失败，缺少权限: {}", loginUser.getUsername(), method.getName(), Arrays.toString(requiredRoles));
            throw new BaseException(HttpStatusEnum.FORBIDDEN.getCode(), "无权访问，需要角色: " + Arrays.toString(requiredRoles));
        }

        // 4. 权限校验通过，执行原方法
        return point.proceed();
    }
}

