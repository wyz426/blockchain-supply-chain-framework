package com.blc.scm.pass.rabbitmq;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Elaine Huang
 * @date 2024/3/19 2:36 PM
 * @signature Do it while you can!
 */
//@Configuration
public class RabbitConfig {

    @Bean
    public Queue fabricQueue() {
        return new Queue("fabricOperations");
    }

    @Bean
    public Queue channelQueue() {
        return new Queue("channelOperations");
    }

    @Bean
    public Queue chaincodeQueue() {
        return new Queue("chaincodeOperations");
    }
}
