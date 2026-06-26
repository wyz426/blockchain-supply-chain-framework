package com.blc.scm.pass.FabricClient;

import lombok.Data;

/**
 * @author Elaine Huang
 * @date 2024/4/26 12:58 AM
 * @signature Do it while you can!
 */
@Data
public class OrgProperty {
    private String certificatePath;
    private String privateKeyPath;
    private String networkConfigPath;
}
