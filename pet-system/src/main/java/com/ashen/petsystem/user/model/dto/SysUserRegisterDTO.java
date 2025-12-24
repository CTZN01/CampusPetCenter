package com.ashen.petsystem.user.model.dto;

import com.ashen.petcommon.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户注册DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRegisterDTO extends BaseEntity {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;
}
