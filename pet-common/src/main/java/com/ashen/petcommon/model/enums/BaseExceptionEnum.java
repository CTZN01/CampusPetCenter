package com.ashen.petcommon.model.enums;

import lombok.Getter;

@Getter
public enum BaseExceptionEnum {

    // ===== 参数异常 =====
    PARAM_MISSING(
            ErrorCodeEnum.PARAM_ERROR,
            "000001",
            "缺少必要参数"
    ),
    PARAM_INVALID(
            ErrorCodeEnum.PARAM_ERROR,
            "000002",
            "参数不合法"
    ),

    // ===== 认证 / 权限 =====
    UNAUTHORIZED(
            ErrorCodeEnum.AUTH_ERROR,
            "000003",
            "未登录或登录已过期"
    ),
    FORBIDDEN(
            ErrorCodeEnum.FORBIDDEN,
            "000004",
            "无访问权限"
    ),

    // ===== 用户业务异常 =====
    USER_NOT_EXIST(
            ErrorCodeEnum.USER_ERROR,
            "000005",
            "用户不存在"
    ),
    USER_DISABLED(
            ErrorCodeEnum.USER_ERROR,
            "000006",
            "用户已被禁用"
    ),
    USER_PASSWORD_ERROR(
            ErrorCodeEnum.USER_ERROR,
            "000007",
            "用户名或密码错误"
    ),

    // ===== 系统异常 =====
    SYSTEM_ERROR(
            ErrorCodeEnum.SYSTEM_ERROR,
            "000008",
            "系统异常"
    );

    /**
     * 错误码大类
     */
    private final ErrorCodeEnum errorCode;

    /**
     * 同一错误码下的异常序号
     */
    private final String seq;

    /**
     * 默认错误信息
     */
    private final String message;

    BaseExceptionEnum(ErrorCodeEnum errorCode, String seq, String message) {
        this.errorCode = errorCode;
        this.seq = seq;
        this.message = message;
    }

}