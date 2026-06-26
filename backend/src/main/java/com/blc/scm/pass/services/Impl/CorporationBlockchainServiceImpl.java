package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.CorporationBlockchainMapper;
import com.blc.scm.pass.models.CorporationBlockchain;
import com.blc.scm.pass.models.SystemModule;
import com.blc.scm.pass.services.CorporationBlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CorporationBlockchainServiceImpl extends ServiceImpl<CorporationBlockchainMapper, CorporationBlockchain> implements CorporationBlockChainService {

    private final CorporationBlockchainMapper mapper;

    @Autowired
    public CorporationBlockchainServiceImpl(CorporationBlockchainMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Integer> getCorporationIdsByBlockChainId(Integer blockchain_id) {
        QueryWrapper<CorporationBlockchain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blockchain_id", blockchain_id);
        List<CorporationBlockchain> corporationBlockchains = mapper.selectList(queryWrapper);

        List<Integer> corporationIds = new ArrayList<>();
        for (CorporationBlockchain corporationBlockchain : corporationBlockchains) {
            corporationIds.add(corporationBlockchain.getCorporationId());
        }
        return corporationIds;
    }

    @Override
    public List<String> getIdentifiersByBlockChainId(Integer blockchain_id) {
        QueryWrapper<CorporationBlockchain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blockchain_id", blockchain_id);
        List<CorporationBlockchain> corporationBlockchains = mapper.selectList(queryWrapper);

        List<String> identifiers = new ArrayList<>();
        for (CorporationBlockchain corporationBlockchain : corporationBlockchains) {
            identifiers.add(corporationBlockchain.getIdentifier());
        }
        return identifiers;
    }
}
