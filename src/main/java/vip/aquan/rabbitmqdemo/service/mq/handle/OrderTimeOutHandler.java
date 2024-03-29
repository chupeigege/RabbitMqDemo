package vip.aquan.rabbitmqdemo.service.mq.handle;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import vip.aquan.rabbitmqdemo.util.MqConstants;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;


/**
 * @description: 订单超时处理器
 * @author: wcp
 * @create: 2020-11-10 16:02
 **/
@Component
@RabbitListener(queues = MqConstants.DELAY_ORDER_TIMEOUT, concurrency = "100")
public class OrderTimeOutHandler {

    @RabbitHandler
    public void process(String content, Channel channel, Message message) {
        try {
            System.out.println(LocalDateTime.now().toString() + ": 订单超时的用户信息Start："+content);
            Thread.sleep(new Random().nextInt(10000));
            System.out.println(LocalDateTime.now().toString() + ": 订单超时的用户信息End："+content);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                //手动确认
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
