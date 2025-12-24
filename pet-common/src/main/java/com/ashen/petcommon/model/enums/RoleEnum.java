package com.ashen.petcommon.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN("admin", "管理员"),
    USER("user", "普通用户");

    private final String code;
    private final String description;
}
