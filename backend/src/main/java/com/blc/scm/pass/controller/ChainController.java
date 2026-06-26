package com.blc.scm.pass.controller;

import com.blc.scm.pass.models.Chaincode;
import com.blc.scm.pass.models.Channel;
import com.blc.scm.pass.services.Impl.ChainCodeServiceImpl;
import com.blc.scm.pass.services.Impl.ChannelChainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chaincode")
public class ChainController {
    @Autowired
    private final ChainCodeServiceImpl chainCodeService;

    @Autowired
    private final ChannelChainServiceImpl channelChainService;

    public ChainController(ChainCodeServiceImpl chainCodeService, ChannelChainServiceImpl channelChainService) {
        this.chainCodeService = chainCodeService;
        this.channelChainService = channelChainService;
    }

    @PostMapping("/")
    public Chaincode createChainCode(@RequestBody Chaincode chaincode, @RequestParam("channelId") Integer channelId) {
        chainCodeService.save(chaincode);
        channelChainService.createChannelChainCodeRelation(channelId, chaincode.getId());
        return chaincode;
    }
}
