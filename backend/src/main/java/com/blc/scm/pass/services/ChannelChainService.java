package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.ChannelChain;

public interface ChannelChainService extends IService<ChannelChain> {
    void createChannelChainCodeRelation(Integer channelId, Integer id);
}
