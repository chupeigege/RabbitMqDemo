package vip.aquan.rabbitmqdemo.service.mq.handle;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vip.aquan.rabbitmqdemo.pojo.DLXMessage;
import vip.aquan.rabbitmqdemo.service.mq.MqService;
import vip.aquan.rabbitmqdemo.util.MqConstants;

import java.io.IOException;

/**
 * @description: 死信队列转发器
 * @author: wcp
 * @create: 2020-11-10 16:02
 **/
@Component
@RabbitListener(queues = MqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME,concurrency = "200")
public class DeadQueueForwarder {

    private static final Logger logger = LoggerFactory.getLogger(DeadQueueForwarder.class);

    @Autowired
    private MqService mqService;

    @RabbitHandler
    public void process(String content, Channel channel, Message messages) {
        try {
            logger.info("死信转发队列::"+content);
            DLXMessage message =  JSON.parseObject(content, DLXMessage.class);
            mqService.send(message.getQueueName(), message.getContent());
        }finally {
            try {
                channel.basicAck(messages.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
