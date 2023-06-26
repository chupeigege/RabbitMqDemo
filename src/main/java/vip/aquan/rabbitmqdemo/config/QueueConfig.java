package vip.aquan.rabbitmqdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.aquan.rabbitmqdemo.util.MqConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 队列声明
 * @author: wcp
 * @create: 2020-05-13 11:22
 **/
@Configuration
public class QueueConfig {

    @Bean
    public Queue orderTimeOutQueue() {
        //订单超时队列
        return new Queue(MqConstants.DELAY_ORDER_TIMEOUT,true,false,false);
    }

    @Bean
    public Binding orderTimeOutQueueBinding() {
        return BindingBuilder.bind(orderTimeOutQueue()).to(defaultExchange()).with(MqConstants.DELAY_ORDER_TIMEOUT);
    }

    @Bean
    public DirectExchange defaultExchange() {
        //业务交换机
        return new DirectExchange(MqConstants.DEFAULT_EXCHANGE, true, false);
    }

    @Bean
    public Queue topicQueue() {
        //主题队列
        return new Queue(MqConstants.TOPIC_QUEUE,true,false,false);
    }

    @Bean
    public TopicExchange topicExchange() {
        //主题交换机
        return new TopicExchange(MqConstants.TOPIC_EXCHANGE, true, false);
    }

    @Bean
    public Binding topicQueueBinding() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with("*.error");
    }

    @Bean
    public Queue deadQueue(){
        //死信队列
        return new Queue(MqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME,true,false,false);
    }

    @Bean
    public Binding deadQueueBiding(){
        //绑定死信队列和死信交换机、路由key
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(MqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME).noargs();
    }

    @Bean
    public CustomExchange deadExchange(){
        //死信交换机，使用插件rabbitmq_delayed_message_exchange
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(MqConstants.DEAD_EXCHANGE, "x-delayed-message",true,false, arguments);
    }
}
