package com.ashen.petsystem.user.service;

import com.ashen.petcommon.model.Result;
import com.ashen.petsystem.user.model.dto.SysUserLoginDTO;
import com.ashen.petsystem.user.model.dto.SysUserPasswordDTO;
import com.ashen.petsystem.user.model.dto.SysUserRegisterDTO;
import com.ashen.petsystem.user.model.dto.SysUserUpdateDTO;
import com.ashen.petsystem.user.model.entity.SysUser;
import com.ashen.petsystem.user.model.vo.SysUserInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletResponse;

/**
* @author 17868
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2025-12-14 22:56:48
*/
public interface SysUserService extends IService<SysUser> {
    SysUserRegisterDTO registerUser(SysUserRegisterDTO sysUserRegisterDTO);

    Result<SysUserInfoVO> loginUser(SysUserLoginDTO loginDTO, HttpServletResponse response);

    SysUserInfoVO getUserInfo(Long curUserId);

    Boolean updateUserInfo(Long curUserId, SysUserUpdateDTO updateDTO);

    Boolean changePassword(Long curUserId, SysUserPasswordDTO passwordDTO);

    PageInfo<SysUserInfoVO> listPage(SysUser sysUser);

    Boolean disableUser(Long userId);
}
