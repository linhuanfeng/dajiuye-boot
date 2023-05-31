package com.lhf.dajiuye.service.user;


//import com.lhf.dajiuye.api.bean.user.LoginInfo;
//import com.lhf.dajiuye.api.bean.user.User;
//import com.lhf.dajiuye.api.dto.OpenIdTokenDto;

import com.lhf.dajiuye.bean.user.LoginInfo;
import com.lhf.dajiuye.bean.user.User;
import com.lhf.dajiuye.dto.OpenIdTokenDto;

public interface WxService {
    User getUserInfo(LoginInfo loginInfo);
    String getOpenIdByCode(String code);
    OpenIdTokenDto getOpenIdToken(String code);
    String checkToken(String token);
}
