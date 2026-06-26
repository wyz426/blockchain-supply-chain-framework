package com.blc.scm.pass.controller;

import com.blc.scm.pass.models.SystemEntity;
import com.blc.scm.pass.services.Impl.SystemEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemEntityController {

    private final SystemEntityServiceImpl systemServiceImpl;

    @Autowired
    public SystemEntityController(SystemEntityServiceImpl systemService) {
        this.systemServiceImpl = systemService;
    }

    @PostMapping("/")
    public SystemEntity createSystem(@RequestBody SystemEntity system){
        systemServiceImpl.save(system);
        return system;
    }

//    @PostMapping("/")
//    public SystemEntity createSystem(@RequestBody SystemEntity system) {
//        Integer blockchainId = system.getBlockchain_id();
//        // 查询对应的区块链实体
//        BlockChain blockChain = blockChainServiceImpl.getById(blockchainId);
//        if (blockChain == null) {
//            throw new BlockchainNotFoundException("区块链不存在");
//        }
//        if (blockChain.getStatus() != 1) { // 假设 1 为正常运行状态
//            throw new BlockchainNotRunningException("区块链没有正常运行");
//        }
//        // 区块链存在且正在运行，保存系统实体
//        systemServiceImpl.save(system);
//        return system;
//    }

    @GetMapping("/id/{system_id}")
    public SystemEntity getSystem(@PathVariable Integer system_id) {
        return systemServiceImpl.getById(system_id);
    }

    @GetMapping("/")
    public List<SystemEntity> getAll() {
        return systemServiceImpl.list();
    }
}
