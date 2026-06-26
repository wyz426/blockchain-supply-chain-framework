package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.BlockChainMapper;
import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.services.BlockChainService;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class BlockChainServiceImpl extends ServiceImpl<BlockChainMapper, BlockChain> implements BlockChainService {

  private final BlockChainMapper blockChainMapper;

  public BlockChainServiceImpl(BlockChainMapper blockChainMapper) {
    this.blockChainMapper = blockChainMapper;
  }

  @Override
  public BlockChain getByName(String name) {
    QueryWrapper<BlockChain> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("name", name);
    return blockChainMapper.selectOne(queryWrapper);
  }
  @Override
  public boolean save(BlockChain blockChain){
    blockChain.setStatus(0);
    Date date=new Date();
    String dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    System.err.println(dateStr);
    blockChain.setCreatedTime(dateStr);

    return blockChainMapper.insert(blockChain)>0;
    //生产一条创建区块链的消息
  }
  @Override
  public BlockChain removeByUid(String uid) {
    QueryWrapper<BlockChain> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_uuid", uid);
    BlockChain blockChain = blockChainMapper.selectOne(queryWrapper);

    if (blockChain != null) {
      int rowsAffected = blockChainMapper.delete(queryWrapper);
      if (rowsAffected > 0) {
        return blockChain;
      }
    }

    return null; // 如果删除失败或未找到匹配的记录，则返回null
  }

  @Override
  public BlockChain removeByName(String name) {
    QueryWrapper<BlockChain> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("name", name);
    BlockChain blockChain = blockChainMapper.selectOne(queryWrapper);

    if (blockChain != null) {
      int rowsAffected = blockChainMapper.delete(queryWrapper);
      if (rowsAffected > 0) {
        return blockChain;
      }
    }

    return null;
  }
}
