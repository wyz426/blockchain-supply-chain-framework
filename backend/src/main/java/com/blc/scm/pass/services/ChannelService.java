package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.BlockChainMember;
import com.blc.scm.pass.models.Channel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Elaine Huang
 * @date 2024/3/25 10:01 PM
 * @signature Do it while you can!
 */
public interface ChannelService {
    void createChannel(String network, String channelName, List<BlockChainMember> orgs);

    void generateConfigtx(String channelName, String txFile, List<BlockChainMember> orgs);

    void updateChannel();

    void delChannel();

    void joinChannel(String network, String channelName, List<BlockChainMember> orgs);
}
