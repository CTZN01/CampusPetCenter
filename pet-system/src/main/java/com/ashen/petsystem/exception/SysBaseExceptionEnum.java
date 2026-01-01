package com.ashen.petsystem.exception;

import com.ashen.petcommon.exception.ExceptionInfo;
import com.ashen.petcommon.model.enums.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysBaseExceptionEnum implements ExceptionInfo {
    SysBaseException000001(ErrorCodeEnum.PARAM_ERROR,"000001","参数缺失"),
    SysBaseException000002(ErrorCodeEnum.PARAM_ERROR,"000002","密码不能为空"),
    SysBaseException000003(ErrorCodeEnum.USER_ERROR,"000003","用户不存在"),
    SysBaseException000004(ErrorCodeEnum.USER_ERROR,"000004","用户名或密码错误"),
    SysBaseException000005(ErrorCodeEnum.PARAM_ERROR,"000005","旧密码错误"),
    SysBaseException000006(ErrorCodeEnum.USER_ERROR,"000006","角色已存在"),
    SysBaseException000007(ErrorCodeEnum.USER_ERROR,"000007","用户已存在"),
    SysBaseException000008(ErrorCodeEnum.USER_ERROR,"000008","角色不存在"),





    ;
    private final ErrorCodeEnum errorCode;
    private final String code;
    private final String message;
}
