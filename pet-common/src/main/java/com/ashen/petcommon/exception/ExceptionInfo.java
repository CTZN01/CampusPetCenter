package com.ashen.petcommon.exception;

import com.ashen.petcommon.model.enums.ErrorCodeEnum;

public interface ExceptionInfo {
    ErrorCodeEnum getErrorCode();
    String getCode();
    String getMessage();
}