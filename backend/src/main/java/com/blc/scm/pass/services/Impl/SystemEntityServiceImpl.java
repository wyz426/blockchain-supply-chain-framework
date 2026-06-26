package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.SystemEntityMapper;
import com.blc.scm.pass.models.SystemEntity;
import com.blc.scm.pass.services.SystemEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemEntityServiceImpl extends ServiceImpl<SystemEntityMapper,SystemEntity> implements SystemEntityService {
    private final SystemEntityMapper systemEntityMapper;

    @Autowired
    public SystemEntityServiceImpl(SystemEntityMapper systemEntityMapper) {
        this.systemEntityMapper = systemEntityMapper;
    }

}
