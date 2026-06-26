package com.blc.scm.pass.controller;

import com.blc.scm.pass.models.Channel;
import com.blc.scm.pass.models.CustomField;
import com.blc.scm.pass.services.Impl.ChannelServiceImpl;
import com.blc.scm.pass.services.Impl.ModuleChannelServiceImpl;
import com.blc.scm.pass.services.Impl.ModuleFieldServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private final ChannelServiceImpl channelService;

    @Autowired
    private final ModuleChannelServiceImpl moduleChannelService;

    public ChannelController(ChannelServiceImpl channelService, ModuleChannelServiceImpl moduleChannelService) {
        this.channelService = channelService;
        this.moduleChannelService = moduleChannelService;
    }

    @PostMapping("/")
    public Channel createChannel(@RequestBody Channel channel, @RequestParam("moduleId") Integer moduleId) {
        channelService.save(channel);
        moduleChannelService.createModuleChannelRelation(moduleId, channel.getId());
        return channel;
    }

    @GetMapping("/channel")
    public Channel getChannel(@RequestParam("moduleId") Integer moduleId) {
        return moduleChannelService.getChannelByModuleId(moduleId);
    }

}
