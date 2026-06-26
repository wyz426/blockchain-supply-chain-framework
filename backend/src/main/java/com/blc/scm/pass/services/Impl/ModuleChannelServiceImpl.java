package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.ModuleChannelMapper;
import com.blc.scm.pass.models.Channel;
import com.blc.scm.pass.models.ModuleChannel;
import com.blc.scm.pass.models.ModuleField;
import com.blc.scm.pass.services.ModuleChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleChannelServiceImpl extends ServiceImpl<ModuleChannelMapper, ModuleChannel> implements ModuleChannelService {
    @Autowired
    private final ModuleChannelMapper moduleChannelMapper;

    @Autowired
    private final ChannelServiceImpl channelServiceImpl;

    public ModuleChannelServiceImpl(ModuleChannelMapper moduleChannelMapper, ChannelServiceImpl channelServiceImpl) {
        this.moduleChannelMapper = moduleChannelMapper;
        this.channelServiceImpl = channelServiceImpl;
    }

    @Override
    public void createModuleChannelRelation(Integer moduleId, Integer channelId) {
        ModuleChannel moduleChannel = new ModuleChannel();
        moduleChannel.setModuleId(moduleId);
        moduleChannel.setChannelId(channelId);
        moduleChannelMapper.insert(moduleChannel);
    }

    @Override
    public Channel getChannelByModuleId(Integer moduleId) {
        QueryWrapper<ModuleChannel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("module_id", moduleId);
        ModuleChannel moduleChannel = moduleChannelMapper.selectOne(queryWrapper);
        if (moduleChannel != null) {
            return channelServiceImpl.getById(moduleChannel.getChannelId());
        }
        return null;
    }
}
