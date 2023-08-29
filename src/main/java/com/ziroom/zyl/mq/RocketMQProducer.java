package com.ziroom.zyl.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Slf4j
public class RocketMQProducer {

    @Resource
    private DefaultMQProducer defaultMQProducer;

    @Value("${rocketmq.consumer.notify.topics}")
    private String topic;

    public void sendTopic(Object msg) {
        try {
            Message message = new Message(topic, JSON.toJSONBytes(msg));

            defaultMQProducer.send(message, defauteSendCallback());

        } catch (MQClientException | RemotingException | InterruptedException e) {
            log.error("sendMQ.sendTopic ERROR topic:{}", topic, e);
        }
    }

    private SendCallback defauteSendCallback(){
        return new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("sender.onSuccess mqMsgId:{}, sendStatus:{}, offsetId:{}",
                        sendResult.getMsgId(), sendResult.getSendStatus(), sendResult.getOffsetMsgId());
            }

            @Override
            public void onException(Throwable e) {
                log.error("sender.onException ERROR topic:{}", topic, e);
            }
        };
    }
}
