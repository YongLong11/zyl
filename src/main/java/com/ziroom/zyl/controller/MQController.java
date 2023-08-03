package com.ziroom.zyl.controller;

import com.alibaba.fastjson.JSON;
import com.ziroom.zyl.common.Resp;
import com.ziroom.zyl.mq.SendMQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/mq")
public class MQController {

    @Resource
    private SendMQ sendMQ;

    @GetMapping("/send")
    public Resp sendMQ(){
        sendMQ.sendTopic("哈哈哈");
        return Resp.success();
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONBytes("哈哈哈"));
    }
}
