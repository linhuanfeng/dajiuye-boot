package com.lhf.dajiuye.service.user;


//import com.lhf.dajiuye.api.bean.user.Userdeliver;

import com.lhf.dajiuye.bean.user.Userdeliver;

public interface UserDeliverService {
    /**
     * 用户发送简历
     * @param userdeliver
     */
    void saveDeliver(Userdeliver userdeliver);

    /**
     * 更新简历投递状态
     * @param userdeliver
     */
    void updateDeliver(Userdeliver userdeliver);
}
