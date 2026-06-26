package com.blc.scm.pass.controller;

import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.models.Corporation;
import com.blc.scm.pass.models.SystemEntity;
import com.blc.scm.pass.services.BlockChainService;
import com.blc.scm.pass.services.CorporationService;
import com.blc.scm.pass.services.Impl.CorporationBlockchainServiceImpl;
import com.blc.scm.pass.services.Impl.SystemEntityServiceImpl;
import com.blc.scm.pass.services.Impl.SystemModuleServiceImpl;
import com.blc.scm.pass.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/corporation")
public class CorporationController {
    private final CorporationService corporationService;
    private final UserService userService;

    private final BlockChainService blockChainService;

    private final SystemModuleServiceImpl systemModuleServiceImpl;

    private final SystemEntityServiceImpl systemEntityService;

    private final CorporationBlockchainServiceImpl corporationBlockchainServiceImpl;
    @Autowired
    public CorporationController(CorporationService corporationService, UserService userService, BlockChainService blockChainService, SystemModuleServiceImpl systemModuleServiceImpl, SystemEntityServiceImpl systemEntityService, CorporationBlockchainServiceImpl corporationBlockchainServiceImpl) {
        this.corporationService = corporationService;
        this.userService = userService;
        this.blockChainService = blockChainService;
        this.systemModuleServiceImpl = systemModuleServiceImpl;
        this.systemEntityService = systemEntityService;
        this.corporationBlockchainServiceImpl = corporationBlockchainServiceImpl;
    }

    /**
     * 根据企业ID获取企业信息
     *
     * @param id 企业ID
     * @return Corporation 企业信息
     */
    @GetMapping("/id/{id}")
    public Corporation getCorporationById(@PathVariable("id") Integer id) {
        return corporationService.getById(id);
    }

    /**
     * 根据用户ID获取企业信息
     *
     * @param id 用户ID
     * @return Corporation 企业信息
     */
    @GetMapping("/uid/{uid}")
    public Corporation getCorporationByUid(@PathVariable("uid")Integer id){
        System.out.println(id);
        String uid=userService.getById(id).getUser_uuid();//通过用户id得到用户uid
        return corporationService.getByUid(uid);//再通过用户uid（即企业uid）查找企业信息
    };

    @GetMapping("/")
    public List<Corporation> getAllCorporations() {
        return corporationService.list();
    }

    @GetMapping("/moduleId/{moduleId}")
    public List<Corporation> getCorporationsByModuleId(@PathVariable("moduleId") Integer moduleId) {
        Integer systemId = systemModuleServiceImpl.getSystemIdByModuleId(moduleId);
        SystemEntity entity = systemEntityService.getById(systemId);
        BlockChain blockChain = blockChainService.getById(entity.getBlockchainid());
        List<Integer> corporationIds = corporationBlockchainServiceImpl.getCorporationIdsByBlockChainId(entity.getBlockchainid());
        return corporationService.listByIds(corporationIds);
    }

    @GetMapping("/systemId/{systemId}")
    public ResponseEntity<Map<String, Object>> getCorporationsBySystemId(@PathVariable("systemId") Integer systemId) {
        SystemEntity entity = systemEntityService.getById(systemId);
        BlockChain blockChain = blockChainService.getById(entity.getBlockchainid());
        List<Integer> corporationIds = corporationBlockchainServiceImpl.getCorporationIdsByBlockChainId(entity.getBlockchainid());
        List<String> identifiers = corporationBlockchainServiceImpl.getIdentifiersByBlockChainId(entity.getBlockchainid());
        List<Corporation> corporations = corporationService.listByIds(corporationIds);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("corporations", corporations);
        responseMap.put("identifiers", identifiers);
        return ResponseEntity.ok(responseMap);
    }

    /**
     * 创建一个新的企业记录，并将其关联到指定用户
     *
     * @param corporation 要创建的企业对象
     * @param id          用户ID，用于关联创建的企业记录到指定用户
     * @return 返回创建的企业对象
     */

    @PostMapping("/{id}")
    public Corporation createCorporation(@RequestBody Corporation corporation,@PathVariable("id") Integer id) {
        String uid=userService.getById(id).getUser_uuid();//通过用户id得到用户uid
        corporation.setUser_uuid(uid);//企业的uid与创建用户的uid相同
        corporationService.save(corporation);
        return corporation;
    }

    @PutMapping("/{id}")
    public Corporation updateCorporation(@PathVariable("id") Integer id, @RequestBody Corporation corporation) {
        corporation.setId(id);
        corporationService.updateById(corporation);//把要更新的状态贴进corporation即可
        return corporation;
    }

    @DeleteMapping("/{id}")
    public void deleteCorporation(@PathVariable("id") Integer id) {
        corporationService.removeById(id);
    }
}
