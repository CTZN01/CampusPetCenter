package com.ashen.petsystem.role.model.entity;

import com.ashen.petcommon.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户角色关联表
 * @TableName sys_user_role
 */
@Data
@Accessors(chain = true)
@TableName(value ="sys_user_role")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {
    @TableId
    @Schema(description = "用户角色ID")
    private Long userRoleId;

    @TableField("USER_ID")
    @Schema(description = "用户ID")
    private Long userId;

    @TableField("ROLE_ID")
    @Schema(description = "角色ID")
    private Long roleId;
}