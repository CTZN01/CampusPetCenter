package com.ashen.petsystem.user.model.dto;

import lombok.Data;

/**
 * 用户信息修改 DTO
 */
@Data
public class SysUserUpdateDTO {
    private Long userId; // 通常从 Token 获取，也可以作为参数传入
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private Integer sex;
    private String realName;
}