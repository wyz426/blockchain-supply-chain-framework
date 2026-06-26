package com.blc.scm.pass.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Elaine Huang
 * @date 2024/4/2 2:04 AM
 * @signature Do it while you can!
 */
@ToString
@AllArgsConstructor
public enum ChainStatus {
    Running(1, "正在运行"),
//    Building,
    ;
    @Getter
    private int code;
    @Getter
    private String msg;
}
