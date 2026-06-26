package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.BlockChainMemberMapper;
import com.blc.scm.pass.models.BlockChainMember;
import com.blc.scm.pass.services.BlockChainMemberService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BlockChainMemberServiceImpl extends ServiceImpl<BlockChainMemberMapper, BlockChainMember>implements BlockChainMemberService {

    private final BlockChainMemberMapper blockChainMemberMapper;

    public BlockChainMemberServiceImpl(BlockChainMemberMapper blockChainMemberMapper) {
        this.blockChainMemberMapper = blockChainMemberMapper;
    }

    @Override
    public BlockChainMember getByName(String name) {
        QueryWrapper<BlockChainMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        BlockChainMember blockChainMember = blockChainMemberMapper.selectOne(queryWrapper);
        return blockChainMember;
    }

    @Override
    public List<BlockChainMember> getByBlockChainName(String blockchainName){
        QueryWrapper<BlockChainMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("blockchain_name",blockchainName);
        return blockChainMemberMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteByName(String name) {
        QueryWrapper<BlockChainMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        blockChainMemberMapper.delete(queryWrapper);
    }


    @Override
    public boolean save(BlockChainMember blockChainMember){
        Date date=new Date();
        String dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        System.err.println(dateStr);
        blockChainMember.setCreatedTime(dateStr);
        return blockChainMemberMapper.insert(blockChainMember)>0;
    }
}
