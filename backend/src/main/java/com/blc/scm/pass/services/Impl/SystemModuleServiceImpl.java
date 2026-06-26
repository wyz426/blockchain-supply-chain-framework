package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.SystemModuleMapper;
import com.blc.scm.pass.models.Module;
import com.blc.scm.pass.models.SystemModule;
import com.blc.scm.pass.services.SystemModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemModuleServiceImpl extends ServiceImpl<SystemModuleMapper, SystemModule> implements SystemModuleService {

    private final SystemModuleMapper systemModuleMapper;

    private final ModuleServiceImpl moduleService;

    @Autowired
    public SystemModuleServiceImpl(SystemModuleMapper systemModuleMapper, ModuleServiceImpl moduleService) {
        this.systemModuleMapper = systemModuleMapper;
        this.moduleService = moduleService;
    }

    @Override
    public void createSystemModuleRelation(Integer systemId, Integer moduleId) {
        // 创建系统和模块的关联记录
        SystemModule systemModule = new SystemModule();
        systemModule.setSystemId(systemId);
        systemModule.setModuleId(moduleId);
        systemModuleMapper.insert(systemModule);
    }

    @Override
    public Integer getSystemIdByModuleId(Integer moduleId) {
        QueryWrapper<SystemModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("module_id", moduleId);
        SystemModule systemModule = systemModuleMapper.selectOne(queryWrapper);
        return systemModule.getSystemId();
    }
    public List<Integer> getModuleIdsBySystemId(Integer systemId) {
        QueryWrapper<SystemModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_id", systemId);
        List<SystemModule> systemModules = systemModuleMapper.selectList(queryWrapper);

        List<Integer> moduleIds = new ArrayList<>();
        for (SystemModule systemModule : systemModules) {
            moduleIds.add(systemModule.getModuleId());
        }
        return moduleIds;
    }

    public List<Module> getModulesBySystemId(Integer systemId) {
        List<Integer> moduleIds = getModuleIdsBySystemId(systemId);
        return moduleService.listByIds(moduleIds);
    }
}
