package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.BlockChain;

public interface BlockChainService extends IService<BlockChain> {
    public BlockChain getByName(String name);

    public BlockChain removeByUid(String uid);

    public BlockChain removeByName(String name);

    public boolean save(BlockChain blockChain);
}
