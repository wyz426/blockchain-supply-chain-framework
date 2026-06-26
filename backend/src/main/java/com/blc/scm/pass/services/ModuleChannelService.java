package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.Channel;
import com.blc.scm.pass.models.ModuleChannel;

public interface ModuleChannelService extends IService<ModuleChannel> {
    void createModuleChannelRelation(Integer moduleId, Integer id);

    Channel getChannelByModuleId(Integer moduleId);
}
