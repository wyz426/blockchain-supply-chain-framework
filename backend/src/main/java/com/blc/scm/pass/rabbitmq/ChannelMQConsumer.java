package com.blc.scm.pass.rabbitmq;

import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.models.BlockChainMember;
import com.blc.scm.pass.services.ChannelService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Elaine Huang
 * @date 2024/6/5 2:54 PM
 * @signature Do it while you can!
 */
//@Component
//@RabbitListener(queues = "channelOperations")
public class ChannelMQConsumer {
    @Autowired
    private ChannelService channelService;

    @RabbitHandler
    public void receive(FabricOperationRequest request) {
        switch (request.getOperation()) {
            //处理构建区块链网络事件
            case "createChannel":
                String network = (String) request.getParameters().get("network");
                String channelName = (String) request.getParameters().get("channelName");
                List<BlockChainMember> orgs = (List<BlockChainMember>) request.getParameters().get("orgs");
                channelService.createChannel(network, channelName, orgs);
                break;
        }
    }
}
