package com.blc.scm.pass.Common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class R {
    private int code; // 返回代码

    private String message; // 发送给前端的信息

    private String type;//

    private Boolean success;// 是否成功

    private Object data; // 返回的数据

    /* 设置为静态函数，直接获取类对象*/
    public static R success(String message) {
        R r = new R();
        r.setCode(200);
        r.setMessage(message);
        r.setSuccess(true);
        r.setType("success");
        r.setData(null);
        return r;
    }

    public static R success(String message, Object data) {
        R r = success(message);
        r.setData(data);
        return r;
    }

    public static R warning(String message) {
        R r = error(message);
        r.setType("warning");
        return r;
    }

    public static R error(String message) {
        R r = success(message);
        r.setSuccess(false);
        r.setType("error");
        return r;
    }

    public static R fatal(String message) {
        R r = error(message);
        r.setCode(500);
        return r;
    }

    public static R ok() {
        R r = new R();
        r.setCode(200);
        return r;
    }

}