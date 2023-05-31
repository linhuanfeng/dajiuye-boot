package com.lhf.dajiuye.service.user.impl;

import com.alibaba.fastjson.JSON;
//import com.lhf.dajiuye.api.bean.user.LoginInfo;
//import com.lhf.dajiuye.api.bean.user.User;
//import com.lhf.dajiuye.api.dto.OpenIdTokenDto;
//import com.lhf.dajiuye.api.service.user.MyUserService;
//import com.lhf.dajiuye.api.service.user.RestService;
//import com.lhf.dajiuye.api.service.user.WxService;
//import com.lhf.dajiuye.user.service.config.WxConfig;
//import com.lhf.dajiuye.user.service.utils.JwtUtil;
//import com.lhf.dajiuye.user.service.utils.WxPKCS7Encoder;
//import com.lhf.dajiuye.user.service.utils.WxUtil;
import com.lhf.dajiuye.bean.user.LoginInfo;
import com.lhf.dajiuye.bean.user.User;
import com.lhf.dajiuye.config.WxConfig;
import com.lhf.dajiuye.dto.OpenIdTokenDto;
import com.lhf.dajiuye.service.user.MyUserService;
import com.lhf.dajiuye.service.user.RestService;
import com.lhf.dajiuye.service.user.WxService;
import com.lhf.dajiuye.utils.JwtUtil;
import com.lhf.dajiuye.utils.WxPKCS7Encoder;
import com.lhf.dajiuye.utils.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 解密encryptedData，获取微信用户信息
 * 调用接口获取登录凭证（code）。通过凭证进而换取用户登录态信息，包括用户的唯一标识（openid）及本次登录的
 * 会话密钥（session_key）等。用户数据的加解密通讯需要依赖会话密钥完成。
 *
 * 通过wx.login获取登录凭证code  ，然后调用后台接口wx.request发送参数code调用接口换取openid和session_key,
 * wx.checkSession检测当前用户登录态是否有效
 */
@Slf4j
@Component // 如果要使用容器中的内容，那么自己也要在容器中
public class WxServiceImpl implements WxService {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private RestService restService;

    @Autowired
    private RestTemplate restTemplate;

//    @DubboReference(interfaceClass = MyUserService.class,version = "1.0.0",check = false)
    @Autowired
MyUserService userService;

//    @Autowired
//    GetTokenByOpenIdFeignService tokenByOpenIdFeignService;

    /**
     * 解密用户的数据
     * @param loginInfo
     * @return User 用户信息
     */
    @Override
    public User getUserInfo(LoginInfo loginInfo) {
        String code = loginInfo.getCode();
        String encryptedData = loginInfo.getEncryptedData();
        String iv = loginInfo.getIv();

        // 1.根据code获取sessionKey和openId
        Map tempMap = getSessionKeyOpenId(code);
        String sessionKey = (String)tempMap.get("session_key");
        String openId = (String)tempMap.get("openid");
        // 2.根据sessionKey和 iv解密encryptedData，得到用户信息realMsg
        String realMsg = doGetUserInfo(encryptedData, sessionKey, iv);
        // 2.1.调用工具函数处理用户信息realMsg，得到返回结果
        Map resMap = WxUtil.strToMap(realMsg);
        // 2.2.调用工具函数，加密openId
        return updateUser(openId, sessionKey, resMap);
    }

    @Override
    public String getOpenIdByCode(String code) {
        String url="https://"+wxConfig.getUrl()+"?appid="+wxConfig.getAppid()+"&secret="+wxConfig.getSecret()+"&js_code="+code+"&grant_type="+wxConfig.getGrantType();
        ResponseEntity<String> entity = restTemplate.getForEntity(url,String.class);
        HashMap map = JSON.parseObject(entity.getBody(), HashMap.class);
        String openId=(String) map.get("openid");
        return openId;
    }

    @Override
    public OpenIdTokenDto getOpenIdToken(String code){
        // 获取openId
        String openId = getOpenIdByCode(code);
        // 根据openId生成token
        return OpenIdTokenDto.builder().
                openId(openId)
                .token(JwtUtil.generateJwtTokenHMAC(openId))
                .build();
    }

    @Override
    public String checkToken(String token){
        return JwtUtil.checkToken(token);
    }

    /**
     * 更新用户信息或者插入新用户;同时为map添加userId和resume
     * @param openId
     * @param sessionKey
     * @param map
     * @return User(返回用户)
     */
    public User updateUser(String openId, String sessionKey, Map map){
        Assert.hasText(openId,"openId不能为为空");
        User user = userService.getUserByOpenId(openId);
        String userId;
        String resume="";
        if(user!=null){
            userId=user.getId();
            resume=user.getResume();
            // 因为用户信息可能会变，及时更新
            user.setNickName((String) map.getOrDefault("nickName",""));
            user.setGender((Integer) map.getOrDefault("gender",0));
            user.setCity((String) map.getOrDefault("city",""));
            user.setProvince((String) map.getOrDefault("province",""));
            user.setAvatar((String) map.getOrDefault("avatarUrl",""));
            user.setSessionKey(sessionKey);

            int i = userService.updateUser(user);
            log.info("更新完用户返回值i="+i);
        }else {
            // 当前为新用户
            user = new User();
            user.setOpenId(openId);
            user.setNickName((String) map.getOrDefault("nickName",""));
            user.setGender((Integer) map.getOrDefault("gender",0));
            user.setCity((String) map.getOrDefault("city",""));
            user.setProvince((String) map.getOrDefault("province",""));
            user.setAvatar((String) map.getOrDefault("avatarUrl",""));
            user.setSessionKey(sessionKey);
            userId = String.valueOf(userService.saveUser(user));
        }
        return user;
    }

    /**
     * 获取sessionKey 调用官方接口
     * https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     * 注意这里如果对getSessionKey进行增强的话会失败，因为调用者的不是增强后的方法，而是增强前的方法
     */
    public Map getSessionKeyOpenId(String code){
        String protocol="https";
        String url = wxConfig.getUrl();
        String appid = wxConfig.getAppid();
        String secret = wxConfig.getSecret();
        String js_code=code;
        String grant_type = wxConfig.getGrantType();
        // 请求参数
        HashMap<String, String> jsonMap = new HashMap<>();
        jsonMap.put("appid",appid);
        jsonMap.put("secret",secret);
        jsonMap.put("js_code",js_code);
        jsonMap.put("grant_type",grant_type);

        // 调用http客户端服务向微信接口code2Session获取session_key和openId
        String json = restService.sendGet(protocol, url, jsonMap);
        //        log.info("body是============={}",body);
        HashMap map = JSON.parseObject(json, HashMap.class);
        return map;
    }

    /**
     * 真正处理解密，并返回解密后字符串
     * {"nickName":"moon","gender":0,"language":"zh_CN","city":"","province":"","country":"","avatarUrl":"https://thirdwx.qlogo.cn/mmopen/vi_32/IUekiUPtlw/132",
     * "watermark":{"timestamp":1635261332,"appid":"wx6ba09ab0d4dcc6e3"}}
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    public String doGetUserInfo(String encryptedData, String sessionKey, String iv){
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

            BASE64Decoder base64Decoder = new BASE64Decoder();

            byte[] _encryptedData = base64Decoder.decodeBuffer(encryptedData);

            byte[] _sessionKey = base64Decoder.decodeBuffer(sessionKey);

            byte[] _iv = base64Decoder.decodeBuffer(iv);

            SecretKeySpec secretKeySpec = new SecretKeySpec(_sessionKey, "AES");

            IvParameterSpec ivParameterSpec = new IvParameterSpec(_iv);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(_encryptedData);

            byte[] bytes = WxPKCS7Encoder.decode(original);

            String originalString = new String(bytes, Charset.forName("utf-8"));

            return originalString;

        } catch (Exception ex) {
            return null;
        }
    }
}
