package com.blc.scm.pass.rabbitmq;

import lombok.Data;

import java.util.Map;

/**
 * @author Elaine Huang
 * @date 2024/3/21 4:25 PM
 * @signature Do it while you can!
 */
@Data
public class FabricOperationRequest {
    private String operation; // 如 "createNetwork", "createChannel", "deployChaincode"
    private Map<String, Object> parameters; // 操作所需的参数
}
