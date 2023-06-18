package com.lhf.dajiuye;

//import com.lhf.dajiuye.domain.Message;
//import com.lhf.dajiuye.domain.MessageDetail;

import com.lhf.dajiuye.mapper.user.UserDataMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootTest
class DajiuyeApplicationTests {

    @Autowired
    UserDataMapper userDataMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void getAllMessageTest() {
//        List<Message> allMessage = userDataMapper.getAllMessage("222");
//        for (Message message : allMessage) {
//            System.out.println(message);
//        }
    }

    @Test
    void getAllMessageDetailTest() {
//        List<MessageDetail> allMessageDetail = userDataMapper.getAllMessageDetail("222");
//        for (MessageDetail messageDetail : allMessageDetail) {
//            System.out.println(messageDetail);
//        }
    }

    @Test
    void MessageUtilTest() {
//        List<MessageDetail> allMessageDetail = userDataMapper.getAllMessageDetail("222");
//        MessageUtil.mergeMsg(allMessageDetail,"222");
    }
}
