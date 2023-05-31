## 项目背景
该小程序集求职和招聘为一体，主要功能包括招聘信息的推荐与分类，即时通讯，职位搜索，面经题库展示，简历上传和下载，职位的发布与审核，就业课程秒杀

## 运行截图

![image-20221214153318152](README.assets/image-20221214153318152.png)

#### 后端技术

SpringBoot+Zookeeper++RabbitMQ+WebSockket+Redis+SpringSecurity+JWT 

1、使用Token机制+Redis（Lua）解决了接口幂等性问题，使用SpringSecurity控制资源访问权限 

2、基于WebSocket和Stomp协议搭建起聊天室，并基于RabbitMQ对聊天消息异步持久化 

3、使用Redisson分布式信号量解决商品超卖问题，使用RabbitMQ异步下单并完成可靠消息设计 

4、基于Redis缓存热点职位，并使用分布式锁Redisson解决缓存击穿问题，并搭建Redis-Cluster集群提高扩展性和可用性

## 部署运行
### 前端

dajiuye-mp-boot

使用微信开发者工具导入dajiuye-mp即可运行

### 后端

dajiuye-backend-boot

1、导入数据库，并更改数据库地址

2、更改zookeeper地址，rabbitmq地址，redis地址，kafka地址，elasticsearch

3、即可启动

## 碎碎念

如果是初学Java的朋友也可以跟着流程借鉴这些代码，试着实现这个项目，相信能对你的成长有所帮助！

如果有心，不妨请博主喝杯茶::laughing::

![image-20221214153300453](README.assets/image-20221214153300453.png)

最后的最后，如果你有任何不懂疑问，欢迎添加**扣扣**咨询群【417094004】，开源不易，一定要给我==Star==哦！！也可加个人微信咨询【moon5672369】
