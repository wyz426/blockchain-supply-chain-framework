package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.enums.ChainStatus;
import com.blc.scm.pass.mapper.ApplicationMapper;
import com.blc.scm.pass.mapper.BlockChainMapper;
import com.blc.scm.pass.mapper.ModuleMapper;
import com.blc.scm.pass.mapper.SystemEntityMapper;
import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.models.SysBuildModel;
import com.blc.scm.pass.services.ApplicationService;
import com.blc.scm.pass.models.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {
    @Autowired
    private BlockChainMapper blockChainMapper;
    @Autowired
    private SystemEntityMapper systemMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Override
    public boolean createSystem(SysBuildModel buildModel) {
        //查找依赖的区块链
        QueryWrapper<BlockChain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", buildModel.getSystemInfo().getBlockchainId());
        BlockChain blockChain = blockChainMapper.selectOne(queryWrapper);
        //判断区块链是否存在并且正在运行
        if (blockChain != null && blockChain.getStatus() == ChainStatus.Running.getCode()){
            //存储系统基本信息
            systemMapper.insert(buildModel.getSystemInfo());
            //批量存储按需选择的模块信息
            moduleMapper.insertBatch(buildModel.getModuleInfos());
            return true;
        }
        return false;
    }
}
