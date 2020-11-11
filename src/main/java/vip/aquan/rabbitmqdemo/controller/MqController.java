package vip.aquan.rabbitmqdemo.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.aquan.rabbitmqdemo.pojo.User;
import vip.aquan.rabbitmqdemo.service.mq.MqService;
import vip.aquan.rabbitmqdemo.util.MqConstants;

@RestController
public class MqController {

    @Autowired
    private MqService mqService;


    @RequestMapping("sendDelay")
    public String sendDelay() {
        User user = new User();
        user.setUsername("百里");
        user.setAge(23);
        //释放时间，发送延时队列
        int releaseTime = 10 * 1000;
        mqService.sendDelay(MqConstants.DELAY_ORDER_TIMEOUT, JSON.toJSONString(user), releaseTime);
        System.out.println("发送延时队列成功");
        return "ok";
    }
}
