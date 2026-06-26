package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.ChannelChainMapper;
import com.blc.scm.pass.models.ChannelChain;
import com.blc.scm.pass.models.ModuleChannel;
import com.blc.scm.pass.services.ChannelChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelChainServiceImpl extends ServiceImpl<ChannelChainMapper, ChannelChain> implements ChannelChainService {

    @Autowired
    private final ChannelChainMapper channelChainMapper;

    public ChannelChainServiceImpl(ChannelChainMapper channelChainMapper) {
        this.channelChainMapper = channelChainMapper;
    }

    @Override
    public void createChannelChainCodeRelation(Integer channelId, Integer id) {
        ChannelChain channelChain = new ChannelChain();
        channelChain.setChannel_id(channelId);
        channelChain.setChaincode_id(id);
        channelChainMapper.insert(channelChain);
    }
}
