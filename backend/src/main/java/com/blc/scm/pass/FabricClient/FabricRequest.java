package com.blc.scm.pass.FabricClient;

import lombok.Data;

/**
 * @author Elaine Huang
 * @date 2024/4/26 12:52 AM
 * @signature Do it while you can!
 */
@Data
public class FabricRequest {
    private String channelName;
    private String chaincodeName;
    private String funcName;
    private String dataId;
    private String data;
}
