package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.SystemModule;

public interface SystemModuleService extends IService<SystemModule> {
    void createSystemModuleRelation(Integer systemId, Integer moduleId);

    Integer getSystemIdByModuleId(Integer moduleId);
}
