package com.ashen.petcommon.model;

import com.ashen.petcommon.exception.ExceptionInfo;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String message;

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    // 新增：直接接受通用 ExceptionInfo
    public BaseException(ExceptionInfo info) {
        super(info != null ? info.getMessage() : null);
        this.code = (info != null && info.getErrorCode() != null) ? info.getErrorCode().getCode() : null;
        this.message = info != null ? info.getMessage() : null;
    }


}