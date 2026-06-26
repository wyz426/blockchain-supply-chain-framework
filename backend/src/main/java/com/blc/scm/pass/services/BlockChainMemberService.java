package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.models.BlockChainMember;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BlockChainMemberService extends IService<BlockChainMember> {

    public BlockChainMember getByName(String name);


    List<BlockChainMember> getByBlockChainName(String blockChainName);

    public void deleteByName(String name);

    boolean save(BlockChainMember blockChainMember);
}
