package com.blc.scm.pass.services;

import org.springframework.stereotype.Service;

/**
 * @author Elaine Huang
 * @date 2024/3/25 10:01 PM
 * @signature Do it while you can!
 */
public interface ChaincodeService {
    void packageChaincode(String chaincodeName);
    void installChaincode(String chaincodeName);
    void approveChaincode(String chaincodeName, String network,String channelName);
    void commitChaincode(String chaincodeName, String network,String channelName);
}
