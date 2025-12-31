package com.ashen.petsystem.role.controller;

import com.ashen.petcommon.annotation.RequireRoles;
import com.ashen.petcommon.constant.RoleConstant;
import com.ashen.petcommon.model.Result;
import com.ashen.petsystem.role.model.dto.SysRoleDTO;
import com.ashen.petsystem.role.model.entity.SysRole;
import com.ashen.petsystem.role.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

@RestController
@RequestMapping("/rbac/role")
@Tag(name = "角色管理", description = "角色管理接口")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "分页查询角色")
    @PostMapping("/listPage")
    @RequireRoles(RoleConstant.ADMIN)
    public Result<PageInfo<SysRole>> listPage(@RequestBody SysRoleDTO roleDTO) {
        return Result.success(sysRoleService.listPage(roleDTO));
    }

    @Operation(summary = "获取所有角色列表")
    @GetMapping("/list")
    @RequireRoles(RoleConstant.ADMIN)
    public Result<List<SysRole>> list() {
        return Result.success(sysRoleService.list());
    }

    @Operation(summary = "新增角色")
    @PostMapping("/add")
    @RequireRoles(RoleConstant.ADMIN)
    public Result<Boolean> add(@RequestBody SysRole role) {
        return Result.success(sysRoleService.add(role));
    }

    @Operation(summary = "修改角色")
    @PutMapping("/update")
    @RequireRoles(RoleConstant.ADMIN)
    public Result<Boolean> update(@RequestBody SysRole role) {
        return Result.success(sysRoleService.updateById(role));
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/delete/{roleId}")
    @RequireRoles(RoleConstant.ADMIN)
    public Result<Boolean> delete(@PathVariable Long roleId) {
        return Result.success(sysRoleService.removeById(roleId));
    }
}
