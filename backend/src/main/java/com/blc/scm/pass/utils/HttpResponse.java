package com.blc.scm.pass.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Elaine Huang
 * @date 2024/2/25 5:17 PM
 * @signature Do it while you can!
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse<T> {
    private boolean success;
    private String errMsg;
    private Integer errCode;
    private T data;

    public static <T> HttpResponse success(T data) {
        return new HttpResponse<>(true, null, null, data);
    }

    public static HttpResponse<Void> failure(String errMsg, Integer errCode) {
        return new HttpResponse<>(false, errMsg, errCode, null);
    }
}
