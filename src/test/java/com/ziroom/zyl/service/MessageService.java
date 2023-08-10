package com.ziroom.zyl.service;

import com.ziroom.zyl.client.apiAdaptor.MessageApiAdaptor;
import com.ziroom.zyl.common.MessageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MessageService {

    @Resource
    MessageApiAdaptor messageApiAdaptor;


    @Resource
    MessageHelper messageHelper;

    @Test
    void testSend(){
        messageApiAdaptor.sendWorkWechat("60042077", "hah", true);

    }

    @Test
    void testSend2(){
        messageHelper.sendWorkWechat("60042077", "hah", true);

    }
}
