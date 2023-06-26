package vip.aquan.rabbitmqdemo.service.mq;

public interface MqService {
    /**
     * 正常发送
     * @param queueName
     * @param msg
     */
    void send(String queueName, Object msg);

    /**
     * 根据RoutingKey发送
     * @param key
     * @param msg
     */
    void sendByRoutingKey(String key, Object msg);

    /**
     * 延时发送
     * @param queueName 队列名称
     * @param msg 消息
     * @param times 延时时间，单位毫秒
     */
    void sendDelay(String queueName,String msg,Integer times);
}
