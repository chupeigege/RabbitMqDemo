package vip.aquan.rabbitmqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * 一、实现RabbitMq延时队列的方式
 *
 * 1.创建队列的时候设置队列的“x-message-ttl”属性； 不足：（1）一种TTL设值要定义一个新队列，会导致队列繁杂  （2）一旦消息过期，就会被队列丢弃
 * 2.针对每条消息设置TTL 不足：（1）消息即使过期，也不一定会被马上丢弃，因为消息是否过期是在即将投递到消费者之前判定的，如果当前队列有严重的消息积压情况，则已过期的消息也许还能存活较长时间
 * 比如，先发了一个延时时长为20s的消息，然后发了一个延时时长为2s的消息，结果显示，第二个消息会在等第一个消息成为死信后才会“死亡“。
 *
 * 3.使用RabbitMQ的rabbitmq_delayed_message_exchange插件 *
 * 搭建步骤：
 * （1）装erlang,rabbitmq,配环境变量，启动
 * （2）装延时队列插件https://github.com/rabbitmq/rabbitmq-delayed-message-exchange ，把下载后的ez文件放rabbitmq/plugins下
 * （3）执行命令 rabbitmq-server -detached （防止启用插件报错）
 * （4）执行命令 rabbitmq-plugins enable rabbitmq_delayed_message_exchange （启用插件）
 *
 * 二、防止消息重复消费：逻辑处理，保证幂等性（发送者把id主键发出去，消费者每次判断数据库是否有此记录，有则不做业务处理）
 *
 * 三、消息按顺序执行: 1.一个队列只对一个消费者 ，消费者用内存队列做排队
 *
 * 四、防止消息丢失：
 * 1.生产者把channel设置成confirm模式，这样mq会接收到消息会响应
 * 2.mq消息持久化（队列设置持久化，发送消息设置持久化）
 * 3.消费者取消autoAck模式，处理完业务再ack
 *
 *
 */

@SpringBootApplication
public class RabbitmqdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqdemoApplication.class, args);
    }

}
