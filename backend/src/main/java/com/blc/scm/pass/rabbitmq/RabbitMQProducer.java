package com.blc.scm.pass.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Elaine Huang
 * @date 2024/6/4 11:25 PM
 * @signature Do it while you can!
 */
//消息生产者
@Component
public class RabbitMQProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String queue, FabricOperationRequest request){
        rabbitTemplate.convertAndSend(queue, request);
    }
}
