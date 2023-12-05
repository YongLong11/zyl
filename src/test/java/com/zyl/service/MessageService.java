package com.zyl.service;

import com.zyl.client.apiAdaptor.MessageApiAdaptor;
import com.zyl.common.MessageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

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

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
    }
}
