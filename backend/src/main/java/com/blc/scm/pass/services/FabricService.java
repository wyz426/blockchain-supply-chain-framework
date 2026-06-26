package com.blc.scm.pass.services;

import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.models.BlockChainMember;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Elaine Huang
 * @date 2024/3/21 4:29 PM
 * @signature Do it while you can!
 */
public interface FabricService {
    void buildChain(BlockChain blockChain);

    void setOrderOrg(String networkCode);

    void setPeerOrg(String network, List<BlockChainMember> orgs);

    void startOrder();

    void startOrg();

    void generateGenesis(String networkCode,List<BlockChainMember> orgs);

    void networkUp(String networkCode);
}
