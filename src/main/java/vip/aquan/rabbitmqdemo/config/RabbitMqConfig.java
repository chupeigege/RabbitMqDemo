package vip.aquan.rabbitmqdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: mq配置
 * @author: wcp
 * @create: 2020-11-10 16:02
 **/
@Configuration
public class RabbitMqConfig {
    private final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public AmqpTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        //可以确认消息是否到达了RabbitMQ服务器
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            //成功发送到交换机会触发该回调
            if (ack){
                //表示消息发送成功
                logger.info("mq回调：消息发送成功");
            }else {
                //表示消息成功发送到服务器，但是没有找到交换器，这里可以记录日志，方便后续处理
                logger.warn("ConfirmCallback -> 消息发布到交换器失败，错误原因为：{}", cause);
            }
        });

        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            //表示消息发送到交换机，但是没有找到队列，这里记录日志
            logger.warn("ReturnCallback -> 消息{}，发送到队列失败，应答码：{}，原因：{}，交换器: {}，路由键：{}",
                    message,
                    replyCode,
                    replyText,
                    exchange,
                    routingKey);
        });

        return rabbitTemplate;
    }
}
