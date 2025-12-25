package com.ashen.petsystem.role.model.dto;

import com.ashen.petsystem.role.model.entity.SysUserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUserRoleDTO extends SysUserRole {
}
