package com.ashen.petsystem.user.controller;


import com.ashen.petcommon.model.Result;
import com.ashen.petcommon.utils.UserUtils;
import com.ashen.petsystem.user.model.dto.SysUserLoginDTO;
import com.ashen.petsystem.user.model.dto.SysUserPasswordDTO;
import com.ashen.petsystem.user.model.dto.SysUserRegisterDTO;
import com.ashen.petsystem.user.model.dto.SysUserUpdateDTO;
import com.ashen.petsystem.user.model.entity.SysUser;
import com.ashen.petsystem.user.model.vo.SysUserInfoVO;
import com.ashen.petsystem.user.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "用户接口", description = "用户接口")
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "用户注册", description = "用户注册接口")
    @PostMapping("/register")
    public Result<SysUserRegisterDTO> register(@RequestBody SysUserRegisterDTO registerDTO) {
        return Result.success(sysUserService.registerUser(registerDTO));
    }

    @PostMapping("/login")
    public Result<SysUserLoginDTO> login(@RequestBody SysUserLoginDTO loginDTO, HttpServletResponse response) {
        return sysUserService.loginUser(loginDTO, response);
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<SysUserInfoVO> getUserInfo(HttpServletRequest request) {
        Long curUserId = UserUtils.getCurrentUserId();
        return Result.success(sysUserService.getUserInfo(curUserId));
    }

    @Operation(summary = "修改个人信息")
    @PutMapping("/update")
    public Result<Boolean> updateUserInfo(@RequestBody SysUserUpdateDTO updateDTO, HttpServletRequest request) {
        Long curUserId = UserUtils.getCurrentUserId();
        return Result.success(sysUserService.updateUserInfo(curUserId, updateDTO));
    }

    @Operation(summary = "修改密码")
    @PostMapping("/password/change")
    public Result<Boolean> changePassword(@RequestBody SysUserPasswordDTO passwordDTO, HttpServletRequest request) {
        Long curUserId = UserUtils.getCurrentUserId();
        return Result.success(sysUserService.changePassword(curUserId, passwordDTO));
    }

}
