package com.ashen.petsystem.user.service.impl;

import com.ashen.petcommon.constant.RoleConstant;
import com.ashen.petcommon.model.BaseException;
import com.ashen.petcommon.model.Result;
import com.ashen.petcommon.utils.BeanCopyUtils;
import com.ashen.petcommon.utils.IdGenerator;
import com.ashen.petcommon.utils.JwtUtils;
import com.ashen.petsystem.exception.SysBaseExceptionEnum;
import com.ashen.petsystem.role.model.entity.SysRole;
import com.ashen.petsystem.role.model.entity.SysUserRole;
import com.ashen.petsystem.role.service.SysRoleService;
import com.ashen.petsystem.role.service.SysUserRoleService;
import com.ashen.petsystem.user.model.dto.SysUserLoginDTO;
import com.ashen.petsystem.user.model.dto.SysUserPasswordDTO;
import com.ashen.petsystem.user.model.dto.SysUserRegisterDTO;
import com.ashen.petsystem.user.model.dto.SysUserUpdateDTO;
import com.ashen.petsystem.user.model.vo.SysUserInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ashen.petsystem.user.model.entity.SysUser;
import com.ashen.petsystem.user.service.SysUserService;
import com.ashen.petsystem.user.mapper.SysUserMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 17868
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2025-12-14 22:56:48
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserRegisterDTO registerUser(SysUserRegisterDTO registerDTO) {
        if(registerDTO == null){
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000001);
        }
        // 1. 检查用户名是否存在
        long count = this.count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, registerDTO.getUsername()));
        if (count > 0) {
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000003);
        }
        SysUser sysUser = new SysUser();
        BeanCopyUtils.copyProperties(sysUser, registerDTO);
        sysUser.setUserId(IdGenerator.getInstance().generateId());
        // 2. 密码加密
        String rawPassword = registerDTO.getPassword();
        if(StringUtils.isBlank(rawPassword)){
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000002);
        }
        sysUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        // 3. 保存用户信息
        this.save(sysUser);

        // 4. 分配默认角色 (例如: student)
        // 默认角色的 roleKey 是 "student"，如果没有则需要先在数据库创建
        SysRole defaultRole = sysRoleService.getOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleKey, RoleConstant.STUDENT));

        if (defaultRole != null) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserRoleId(IdGenerator.getInstance().generateId());
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(defaultRole.getRoleId());
            sysUserRoleService.save(userRole);
        }

        return registerDTO;
    }

    @Override
    public Result<SysUserInfoVO> loginUser(SysUserLoginDTO loginDTO, HttpServletResponse response) {
        SysUser dbUser = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, loginDTO.getUsername()));
        // 校验用户是否存在
        if(null == dbUser){
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000004);
        }
        // 校验密码
        if(!passwordEncoder.matches(loginDTO.getPassword(), dbUser.getPassword())){
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000004);
        }

        // 获取用户角色列表
        List<SysUserRole> userRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, dbUser.getUserId()));

        List<String> roleKeys = Collections.emptyList();
        if (!userRoles.isEmpty()) {
            List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            List<SysRole> roles = sysRoleService.listByIds(roleIds);
            roleKeys = roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList());
        }

        //生成token
        String token = JwtUtils.generateToken(dbUser.getUsername(), dbUser.getUserId(), new HashSet<>(roleKeys));
        //加上Bearer前缀
        String bearerToken = "Bearer " + token;
        response.setHeader("Authorization", bearerToken);

        // 构建返回结果 (使用 VO 避免泄露密码)
        SysUserInfoVO userInfoVO = new SysUserInfoVO();
        BeanCopyUtils.copyProperties(userInfoVO, dbUser);
        userInfoVO.setRoles(roleKeys);

        return Result.success(userInfoVO);
    }

    @Override
    public SysUserInfoVO getUserInfo(Long userId) {
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000004);
        }
        SysUserInfoVO vo = new SysUserInfoVO();
        BeanCopyUtils.copyProperties(vo, sysUser);

        // 填充角色信息
        List<SysUserRole> userRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));

        if (CollectionUtils.isNotEmpty(userRoles)) {
            List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            List<SysRole> roles = sysRoleService.listByIds(roleIds);
            vo.setRoles(roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList()));
        } else {
            vo.setRoles(Collections.emptyList());
        }

        return vo;
    }

    @Override
    public Boolean updateUserInfo(Long userId, SysUserUpdateDTO updateDTO) {
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000003);
        }
        BeanCopyUtils.copyProperties(sysUser, updateDTO);
        return this.updateById(sysUser);
    }

    @Override
    public Boolean changePassword(Long userId, SysUserPasswordDTO passwordDTO) {
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000003);
        }
        // 1. 校验旧密码
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), sysUser.getPassword())) {
            // 建议使用专门的 "旧密码错误" 异常码
            throw new BaseException(SysBaseExceptionEnum.SysBaseException000005);
        }
        // 2. 加密新密码并保存
        sysUser.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        return this.updateById(sysUser);
    }


}
