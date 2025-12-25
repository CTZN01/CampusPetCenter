package com.ashen.petcommon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解
 * 用于 Controller 方法上，指定该接口需要的角色
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRoles {
    /**
     * 需要的角色列表 (默认只需满足其中一个角色即可)
     * 例如: {RoleConstant.ADMIN, RoleConstant.TEACHER}
     */
    String[] value();
}

