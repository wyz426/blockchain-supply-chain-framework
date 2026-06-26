package com.blc.scm.pass.controller;

import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.models.CorporationBlockchain;
import com.blc.scm.pass.models.SystemEntity;
import com.blc.scm.pass.services.BlockChainService;
import com.blc.scm.pass.services.CorporationBlockChainService;
import com.blc.scm.pass.services.Impl.CorporationBlockchainServiceImpl;
import com.blc.scm.pass.services.Impl.SystemEntityServiceImpl;
import com.blc.scm.pass.services.Impl.SystemModuleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blockchain")
public class BlockChainController {
    private final BlockChainService blockChainService;

    private final SystemModuleServiceImpl systemModuleServiceImpl;

    private final SystemEntityServiceImpl systemEntityService;

    private final CorporationBlockchainServiceImpl corporationBlockchainService;

    @Autowired
    public BlockChainController(BlockChainService consortiumService, SystemModuleServiceImpl systemModuleServiceImpl, SystemEntityServiceImpl systemEntityService, CorporationBlockchainServiceImpl corporationBlockchainService) {
        this.blockChainService = consortiumService;
        this.systemModuleServiceImpl = systemModuleServiceImpl;
        this.systemEntityService = systemEntityService;
        this.corporationBlockchainService = corporationBlockchainService;
    }

    @GetMapping("/{id}")
    public BlockChain getConsortiumById(@PathVariable("id") Integer id) {
        return blockChainService.getById(id);
    }

    /**
     * 根据联盟名称查询联盟信息
     *
     * @param name 联盟名称
     * @return 符合指定名称的联盟对象，如果找不到匹配的联盟则返回null
     */
    @GetMapping("/name/{name}")
    public BlockChain getConsortiumByName(@PathVariable("name") String name) {
        return blockChainService.getByName(name);
    }
    @GetMapping("/")
    public List<BlockChain> getAllConsortiums() {
        return blockChainService.list();
    }

    @GetMapping("/moduleId/{moduleId}")
    public BlockChain getBlockChainByModuleId(@PathVariable("moduleId") Integer moduleId) {
        Integer systemId = systemModuleServiceImpl.getSystemIdByModuleId(moduleId);
        SystemEntity entity = systemEntityService.getById(systemId);
        return blockChainService.getById(entity.getBlockchainid());
    }

    @GetMapping("/systemId/{systemId}")
    public BlockChain getBlockChainBySystemId(@PathVariable("systemId") Integer systemId) {
        SystemEntity entity = systemEntityService.getById(systemId);
        return blockChainService.getById(entity.getBlockchainid());
    }

    @PostMapping("/")
    public BlockChain createConsortium(@RequestBody BlockChain blockChain) {
        blockChainService.save(blockChain);
        return blockChain;
    }

    @PostMapping("/corporationId/{corporationId}")
    public Integer AddCorporationToBlockChain(@RequestParam("identifier") String identifier, @RequestParam("blockchainId") Integer blockchainId, @PathVariable("corporationId") Integer corporationId) {
        CorporationBlockchain corporationBlockchain = new CorporationBlockchain();
        corporationBlockchain.setCorporationId(corporationId);
        corporationBlockchain.setBlockchainId(blockchainId);
        corporationBlockchain.setIdentifier(identifier);
        corporationBlockchainService.save(corporationBlockchain);

        return corporationId;
    }

    @PutMapping("/{id}")
    public BlockChain updateConsortium(@PathVariable("id") Integer id, @RequestBody BlockChain blockChain) {
        blockChain.setId(id);
        blockChainService.updateById(blockChain);
        return blockChain;
    }

    @DeleteMapping("/name/{name}")
    public void deleteConsortiumByName(@PathVariable("name") String name) {
        blockChainService.removeByName(name);
    }

    /**
     * Deletes a Consortium by its UID.
     *
     * @param uid The UID of the Consortium to be deleted.
     */
    @DeleteMapping("/uid/{uid}")
    public void deleteConsortium(@PathVariable("uid") String uid) {blockChainService.removeByUid(uid);}
}

