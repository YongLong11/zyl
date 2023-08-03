package com.ziroom.zyl.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ConsumerConfig {

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.consumeBatchMaxSize}")
    private int batchMaxSize;
    @Value("${rocketmq.consumer.notify.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.notify.topics}")
    private String topic;
    @Value("${rocketmq.consumer.notify.consumeThreadMin}")
    private int consumerThreadMin;
    @Value("${rocketmq.consumer.notify.consumeThreadMax}")
    private int consumerThreadMax;

    @Bean("defaultMQPushConsumer")
    public DefaultMQPushConsumer defaultMQPushConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumerThreadMin);
        consumer.setConsumeThreadMax(consumerThreadMax);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setConsumeMessageBatchMaxSize(batchMaxSize);
        consumer.registerMessageListener((MessageListenerConcurrently) (megs, context) -> {
            if (CollectionUtils.isEmpty(megs)) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            megs.stream().forEach(meg -> {
                System.out.println("被消费了" + new String(meg.getBody()));
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        try {
            consumer.subscribe(topic, "*");
            // 启动消费
            consumer.start();
            log.info("consumer is started. groupName:{}, topics:{}, namesrvAddr:{}", groupName, topic,
                    namesrvAddr);
        } catch (Exception e) {
            log.error("failed to start consumer . groupName:{}, topics:{}, namesrvAddr:{}", groupName,
                    topic, namesrvAddr, e);
            throw new RuntimeException(e);
        }
        return consumer;
    }
}
