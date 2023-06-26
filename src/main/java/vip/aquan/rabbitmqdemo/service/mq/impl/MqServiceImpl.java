package vip.aquan.rabbitmqdemo.service.mq.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.aquan.rabbitmqdemo.pojo.DLXMessage;
import vip.aquan.rabbitmqdemo.service.mq.MqService;
import vip.aquan.rabbitmqdemo.util.MqConstants;
import vip.aquan.rabbitmqdemo.util.SnowFlakeUtil;

/**
 * @description: mq
 * @author: wcp
 * @create: 2020-11-10 16:02
 **/
@Service
public class MqServiceImpl implements MqService {
    private static final Logger logger = LoggerFactory.getLogger(MqServiceImpl.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String queueName, Object msg) {
        String msgId = SnowFlakeUtil.createSnowflakeId().toString();
        logger.info("send msg，队列名：{}，消息： {}，CorrelationData：{}",queueName,msg,msgId);
        this.rabbitTemplate.convertAndSend(queueName, msg, new CorrelationData(msgId));
    }

    @Override
    public void sendByRoutingKey(String routingKey, Object msg) {
        String msgId = SnowFlakeUtil.createSnowflakeId().toString();
        logger.info("sendByRoutingKey msg，routingKey：{}，消息： {}，CorrelationData：{}",routingKey,msg,msgId);
        this.rabbitTemplate.convertAndSend(MqConstants.TOPIC_EXCHANGE ,routingKey, msg, new CorrelationData(msgId));
    }

    @Override
    public void sendDelay(String queueName, String msg, Integer times) {
        DLXMessage dlxMessage = new DLXMessage(queueName,msg,times);
        MessagePostProcessor processor = (message)->{
            message.getMessageProperties().setDelay(times);
            return message;
        };
        dlxMessage.setExchange(MqConstants.DEFAULT_EXCHANGE);
        // 消息的投递方式默认为2（持久化） 看源码，Message的封装
        logger.info("延时队列发送：队列名：{}，消息：{}，延时时间（毫秒）：{}",queueName, msg, times);
        rabbitTemplate.convertAndSend(MqConstants.DEAD_EXCHANGE,MqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME,
                JSON.toJSONString(dlxMessage), processor);
    }

}
