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
 * 角色表
 * @TableName sys_role
 */
@Data
@Accessors(chain = true)
@TableName(value ="sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {
    @TableId
    @Schema(description = "角色ID")
    private Long roleId;

    @TableField("ROLE_KEY")
    @Schema(description = "角色权限字符串(如: admin, student)")
    private String roleKey;

    @TableField("ROLE_CODE")
    @Schema(description = "角色编码")
    private String roleCode;

    @TableField("ROLE_NAME")
    @Schema(description = "角色名称(如: 管理员, 学生)")
    private String roleName;

    @TableField("IS_ENABLED")
    @Schema(description = "状态: 1-启用, 0-停用")
    private Integer isEnabled;

    @TableField("DESCRIPTION")
    @Schema(description = "角色描述")
    private String description;
}