package com.ashen.petsystem.user.mapper;

import com.ashen.petsystem.user.model.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 17868
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2025-12-14 22:56:48
* @Entity com.ashen.petsystem.user.model.entity.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}




