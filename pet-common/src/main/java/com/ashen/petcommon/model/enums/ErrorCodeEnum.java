package com.ashen.petcommon.model.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    SUCCESS(0),
    PARAM_ERROR(400),
    AUTH_ERROR(401),
    FORBIDDEN(403),
    SYSTEM_ERROR(500),

    // 业务域（10000+）
    USER_ERROR(10001),
    ORDER_ERROR(20001);

    private final Integer code;

    ErrorCodeEnum(Integer code) {
        this.code = code;
    }
}