package vip.aquan.rabbitmqdemo.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.aquan.rabbitmqdemo.pojo.User;
import vip.aquan.rabbitmqdemo.service.mq.MqService;
import vip.aquan.rabbitmqdemo.util.MqConstants;

import java.time.LocalDateTime;

@RestController
public class MqController {

    @Autowired
    private MqService mqService;


    @RequestMapping("sendDelay")
    public String sendDelay(@RequestParam Integer age) {
        User user = new User();
        user.setUsername("百里");
        user.setAge(age);
        //释放时间，发送延时队列
        int releaseTime = 10 * 1000;
//        mqService.sendDelay(MqConstants.DELAY_ORDER_TIMEOUT, JSON.toJSONString(user), releaseTime);
        mqService.send(MqConstants.DELAY_ORDER_TIMEOUT, JSON.toJSONString(user));
        System.out.println(LocalDateTime.now().toString() + "：发送延时队列成功");
        return "ok";
    }

    @RequestMapping("sendByRoutingKey")
    public String sendByRoutingKey(@RequestParam String topic) {
        User user = new User();
        user.setUsername("百里");
        user.setAge(18);
        mqService.sendByRoutingKey(topic, JSON.toJSONString(user));
        System.out.println(LocalDateTime.now().toString() + "：发送topic消息成功");
        return "ok";
    }
}
