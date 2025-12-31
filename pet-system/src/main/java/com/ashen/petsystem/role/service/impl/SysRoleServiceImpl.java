package com.ashen.petsystem.role.service.impl;

import com.ashen.petcommon.model.BaseException;
import com.ashen.petcommon.utils.IdGenerator;
import com.ashen.petcommon.utils.PageSelectUtils;
import com.ashen.petsystem.exception.SysBaseExceptionEnum;
import com.ashen.petsystem.role.model.dto.SysRoleDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ashen.petsystem.role.model.entity.SysRole;
import com.ashen.petsystem.role.service.SysRoleService;
import com.ashen.petsystem.role.mapper.SysRoleMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
* @author 17868
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2025-12-25 10:25:32
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    @Override
    public Boolean add(SysRole role) {
        //查询是否存在相同角色名称
        SysRole existingRole = this.lambdaQuery()
                .eq(SysRole::getRoleKey, role.getRoleKey()).one();
        if (existingRole != null) {
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000006);
        }
        role.setRoleId(IdGenerator.generateId());
        role.setIsEnabled(1);
        return this.save(role);
    }

    @Override
    public PageInfo<SysRole> listPage(SysRoleDTO roleDTO) {
        return PageSelectUtils.selectPage(roleDTO, super::list);
    }
}
