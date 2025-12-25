package com.ashen.petsystem.user.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 用户信息 VO (返回给前端展示)
 */
@Data
public class SysUserInfoVO {
    private Long userId;

    private String username;

    private String nickname;

    private String avatar;

    private String phone;

    private String email;

    private Integer sex;

    private String realName;

    private String userCode;

    private List<String> roles;
}