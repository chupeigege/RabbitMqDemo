package vip.aquan.rabbitmqdemo.service.mq.handle;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import vip.aquan.rabbitmqdemo.util.MqConstants;

import java.io.IOException;
import java.time.LocalDateTime;


/**
 * @description: topic队列处理器
 * @author: wcp
 * @create: 2020-11-10 16:02
 **/
@Component
@RabbitListener(queues = MqConstants.TOPIC_QUEUE, concurrency = "100")
public class TopicHandler {

    @RabbitHandler
    public void process(String content, Channel channel, Message message) {
        try {
            System.out.println(LocalDateTime.now().toString() + ": TopicHandler："+content);
        }finally {
            try {
                //手动确认
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
