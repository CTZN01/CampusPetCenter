package com.ashen.petsystem.user.model.entity;

import com.ashen.petcommon.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表
 * @TableName sys_user
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="sys_user")
@Schema(description = "用户实体")
public class SysUser extends BaseEntity {
    /**
     * 主键ID
     */
    @TableId
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 用户名(登录账号)
     */
    @TableField("USERNAME")
    @Schema(description = "用户名(登录账号)")
    private String username;

    /**
     * 密码(加密)
     */
    @TableField("PASSWORD")
    @Schema(description = "密码(加密)")
    private String password;

    /**
     * 学号/工号
     */
    @TableField("USER_CODE")
    @Schema(description = "学号/工号")
    private String userCode;

    /**
     * 真实姓名
     */
    @TableField("REAL_NAME")
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 昵称
     */
    @TableField("NICKNAME")
    @Schema(description = "昵称")
    private String nickname;
    /**
     * 头像地址
     */
    @TableField("AVATAR")
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 联系电话
     */
    @TableField("PHONE")
    @Schema(description = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    @Schema(description = "邮箱")
    private String email;

    /**
     * 性别: 0-未知, 1-男, 2-女
     */
    @TableField("SEX")
    @Schema(description = "性别: 0-未知, 1-男, 2-女")
    private Integer sex;

    /**
     * 帐号是否启用: 1-启动, 0-禁用
     */
    @TableField("IS_ENABLED")
    @Schema(description = "帐号是否启用: 1-启动, 0-禁用")
    private Integer isEnabled;


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}