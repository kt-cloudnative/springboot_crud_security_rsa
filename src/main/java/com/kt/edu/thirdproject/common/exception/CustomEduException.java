package com.kt.edu.thirdproject.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomEduException extends RuntimeException {

    private static final long serialVersionUID = -6531321298357306786L;

    /** (사용자 정의) Http 상태 코드 값 **/
    private Integer status;

    /** (사용자 정의) Http 상태 메시지(에러 메시지) **/
    private String message;

    /** 실제 발생한 exception 객체 **/
    private Throwable cause;

    public CustomEduException(String message) {
        super(message);
        this.message = message;
    }

    public CustomEduException(String message, Throwable t) {
        super(message, t);
        this.message = message;
    }

    public CustomEduException(Integer status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public CustomEduException(Integer status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.message = message;
        this.cause = cause;
    }
}