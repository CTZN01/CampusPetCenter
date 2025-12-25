package com.ashen.petsystem.role.controller;

import com.ashen.petcommon.annotation.RequireRoles;
import com.ashen.petcommon.constant.RoleConstant;
import com.ashen.petcommon.model.Result;
import com.ashen.petcommon.utils.IdGenerator;
import com.ashen.petsystem.role.model.entity.SysUserRole;
import com.ashen.petsystem.role.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rbac/user-role")
@Tag(name = "用户角色管理", description = "用户角色关联接口")
public class UserRoleController {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Operation(summary = "给用户分配角色")
    @PostMapping("/assign")
    @RequireRoles(RoleConstant.ADMIN)
    public Result<Boolean> assignRole(@RequestBody SysUserRole userRole) {
        // 检查是否已存在
        long count = sysUserRoleService.count(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userRole.getUserId())
                .eq(SysUserRole::getRoleId, userRole.getRoleId()));
        if (count > 0) {
            return Result.success(true);
        }
        userRole.setUserRoleId(IdGenerator.getInstance().generateId());
        return Result.success(sysUserRoleService.save(userRole));
    }

    @Operation(summary = "取消用户角色")
    @PostMapping("/remove")
    @RequireRoles(RoleConstant.ADMIN)
    public Result<Boolean> removeRole(@RequestBody SysUserRole userRole) {
        return Result.success(sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userRole.getUserId())
                .eq(SysUserRole::getRoleId, userRole.getRoleId())));
    }
}
