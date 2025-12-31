package com.ashen.petsystem.role.service;

import com.ashen.petsystem.role.model.dto.SysRoleDTO;
import com.ashen.petsystem.role.model.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
* @author 17868
* @description 针对表【sys_role(角色表)】的数据库操作Service
* @createDate 2025-12-25 10:25:32
*/
public interface SysRoleService extends IService<SysRole> {

    Boolean add(SysRole role);

    PageInfo<SysRole> listPage(SysRoleDTO roleDTO);
}
