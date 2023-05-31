package com.lhf.dajiuye.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisJobHotService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void listPush() {
        BoundListOperations<String, String> listOps = redisTemplate.boundListOps("list:comment:lasted:22");

        for (int i = 0; i < 30; i++) {
            listOps.rightPush("c"+i);
        }

        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,random.nextInt(15));

        listOps.expire(100, TimeUnit.SECONDS);
//        listOps.expireAt(calendar.getTime());
    }

    List<String> hashGet(Set<String> set) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps("hash:comment");

        for (int i = 0; i < 30; i++) {
            hashOps.put("c"+i,"1");
        }

        for (int i = 0; i < 40; i++) {
            hashOps.put(i+"-","i=>我是评论实体");
        }

        List<String> list = hashOps.multiGet(set);
        return list;
    }

    public List<String> listRange(Long qid, Integer page, Integer size) {
        BoundListOperations<String, String> listOps = redisTemplate.boundListOps("list:comment:lasted:"+qid);

        listOps.expire(listOps.getExpire()+100, TimeUnit.SECONDS); // 异步增加过期时间，热度越大自然过期时间越长

        List<String> list = listOps.range((page-1)*size, (page-1)*size+size);


        System.out.println("list:size:"+list.size());
        for (String s : list) {
            System.out.print(s+" ");
        }
        System.out.println();

        listOps.expire(listOps.getExpire()+100,TimeUnit.SECONDS); //续期

        return list;
    }

    Set<String> zSetRange(Long qid, Integer page, Integer size) {
        BoundZSetOperations<String, String> zSetOps = redisTemplate.boundZSetOps("zset:comment:hot:"+qid);

        // 评论被点赞，那么更新权值（直接替换）
        for (int i = 0; i < 40; i++) {
            zSetOps.add(i+"-",i);
        }

        // 分值从大到小对应的key返回,得到最新的评论
        Set<String> set = zSetOps.reverseRange((page - 1) * size, (page - 1) * size + size);
        System.out.println("set:size:"+set.size());
        for (String s : set) {
            System.out.print(s+" ");
        }
        System.out.println();

        // 判断是否需要减少缓存内容
        if(size>30){
            // 批量删除排名30之后的评论
            zSetOps.removeRange(0,size-30);
        }

        // 续命
        zSetOps.expire(zSetOps.getExpire()+100,TimeUnit.SECONDS);

        return set;
    }

    void queryPage(){
        Map<String, Object> params=new HashMap<>();
        Long qid=22l;

        Integer page = (Integer) params.get("page");
        Integer limit = (Integer)params.get("limit");
        page=1;
        limit=10;

        // list获取最新的评论
        List<String> list = listRange(qid, page, limit);

        Set<String> set = zSetRange(qid, page, limit);

       set.addAll(list);

        System.out.println(set.size());
        for (String s : set) {
            System.out.print(s+" ");
        }

        // 再从hash中获取详细的数据
        List<String> objects = hashGet(set);
        System.out.println("objects:size:"+objects.size());
        for (Object object : objects) {
            System.out.print(object+" ");
        }
    }
}
