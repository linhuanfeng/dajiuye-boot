package com.lhf.dajiuye.utils;

import java.util.Random;

public class EncryptUtil {
    public static String encryptUtil(String old){
        // openId进行加密返回客户端
        int len = old.length();
        char[] chars1 = new char[len*2];
        char[] chars2 = old.toCharArray();
        int j=0;
        for (int i = 0; i < len * 2; i++) {
            chars1[i]=chars2[j++];
            char[] myKey={'a','2','s','o','u','b','d','5','=','h'};
            chars1[++i]=myKey[new Random().nextInt(10)];
        }
        return new String(chars1);
    }
    public static String decryptUtil(String old){
        // openId进行加密返回客户端
        int len = old.length();
        char[] chars1 = new char[len/2]; //根据上面的规则，肯定是整数
        char[] chars2 = old.toCharArray();
        int j=0;
        for (int i = 0; i < len/2; i++) {
            chars1[i]=chars2[j];
            j=j+2;
        }
        return new String(chars1);
    }
}
