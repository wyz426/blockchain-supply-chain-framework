package com.blc.scm.pass.rabbitmq;

import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.services.ChaincodeService;
import com.blc.scm.pass.services.FabricService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Elaine Huang
 * @date 2024/6/5 2:54 PM
 * @signature Do it while you can!
 */
//@Component
//@RabbitListener(queues = "chaincodeOperations")
public class ChaincodeMQConsumer {
    @Autowired
    private ChaincodeService chaincodeService;

    @RabbitHandler
    public void receive(FabricOperationRequest request) {
        switch (request.getOperation()) {
            //处理构建区块链网络事件
            case "deployChaincode":
                String name = (String) request.getParameters().get("name");
                String channel = (String) request.getParameters().get("channel");
                String network = (String) request.getParameters().get("network");
                chaincodeService.packageChaincode(name);
                chaincodeService.installChaincode(name);
                chaincodeService.approveChaincode(name, network, channel);
                chaincodeService.commitChaincode(name, network, channel);
                break;
        }
    }
}
