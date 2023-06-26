package vip.aquan.rabbitmqdemo.util;

/**
 * @description: mq常量
 * @author: wcp
 * @create: 2020-11-10 16:02
 **/
public class MqConstants {
    //业务交换机
    public static final String DEFAULT_EXCHANGE = "kshop";

    //死信交换机
    public static final String DEAD_EXCHANGE = "deadExchange";

    //主题交换机
    public static final String TOPIC_EXCHANGE = "topicExchange";

    //DLX repeat QUEUE 死信转发队列
    public static final String DEFAULT_REPEAT_TRADE_QUEUE_NAME = "kshop.repeat.trade.queue";

    /*mq延时队列*/
    //订单超时队列
    public static final String DELAY_ORDER_TIMEOUT =  "orderTimeOut";

    //topic队列
    public static final String  TOPIC_QUEUE=  "topicQueue1";
}
