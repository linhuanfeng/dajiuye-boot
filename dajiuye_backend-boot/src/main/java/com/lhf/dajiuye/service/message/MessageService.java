package com.lhf.dajiuye.service.message;




import com.baomidou.mybatisplus.extension.service.IService;
import com.lhf.dajiuye.bean.message.Message;
import com.lhf.dajiuye.bean.message.MessageDetail;

import java.util.List;

public interface MessageService extends IService<Message> {
    void saveMessage(Message msg);

    List<Message> getAllMessage(String openId);

    List<MessageDetail> getAllMessageDetail(String openId);
}
