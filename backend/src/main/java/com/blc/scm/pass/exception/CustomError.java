package com.blc.scm.pass.exception;

/**
 * @author Elaine Huang
 * @date 2024/2/25 5:14 PM
 * @signature Do it while you can!
 */
public enum CustomError {
    INTERNAL_ERROR(4001, "内部错误"),
    UNKNOWN_ERROR(4002, "未知错误"),
    IMAGE_TYPE_ERROR(4003, "图片类型不符合要求"),
    PIC_SIZE_EXCEED(4004, "图片大小不符合要求"),
    FABRIC_SUBMIT_ERROR(4005,"调用链码失败（无返回值）" ),
    FABRIC_EVALUATE_ERROR(4006, "调用链码失败（有返回值)");

    private int code;

    private String errMsg;

    CustomError(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public int getCode() {
        return this.code;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
