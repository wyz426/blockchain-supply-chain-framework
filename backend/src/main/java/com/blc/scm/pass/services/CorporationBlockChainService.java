package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.CorporationBlockchain;

import java.util.List;

public interface CorporationBlockChainService extends IService<CorporationBlockchain> {
    List<Integer> getCorporationIdsByBlockChainId(Integer blockchain_id);

    List<String> getIdentifiersByBlockChainId(Integer blockchain_id);
}
