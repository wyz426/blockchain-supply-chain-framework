package com.blc.scm.pass.exception;

/**
 * @author Elaine Huang
 * @date 2024/2/25 5:14 PM
 * @signature Do it while you can!
 */
public class LocalRuntimeException extends RuntimeException {
    private CustomError error;

    public LocalRuntimeException(CustomError error) {
        this.error = error;
    }

    public LocalRuntimeException(CustomError error, String message) {
        super(message);
        this.error = error;
    }

    public CustomError getError() {
        return error;
    }

    public LocalRuntimeException(String message, Throwable cause, CustomError error) {
        super(message, cause);
        this.error = error;
    }
}
