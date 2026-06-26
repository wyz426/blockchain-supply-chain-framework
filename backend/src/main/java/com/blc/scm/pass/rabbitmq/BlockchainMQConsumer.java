package com.blc.scm.pass.rabbitmq;

import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.services.FabricService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Elaine Huang
 * @date 2024/3/19 2:38 PM
 * @signature Do it while you can!
 */
//消息消费者
//@Component
//@RabbitListener(queues = "fabricOperations")
public class BlockchainMQConsumer {
    @Autowired
    private FabricService fabricService;

    @RabbitHandler
    public void receive(FabricOperationRequest request) {
        switch (request.getOperation()) {
            //处理构建区块链网络事件
            case "createNetwork":
                //test能不能消费到消息
                System.out.println("success");
                //张兰写的创建区块链函数
                fabricService.buildChain((BlockChain) request.getParameters().get("blockchain"));
                break;
        }
    }
}
