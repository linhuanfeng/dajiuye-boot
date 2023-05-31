package com.lhf.dajiuye.config;


//import com.lhf.dajiuye.token.service.strategy.CheckTokenStrategy;
//import com.lhf.dajiuye.token.service.strategy.LuaCheckTokenStrategy;
//import com.lhf.dajiuye.token.service.strategy.TokenStrategyContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class TokenStrategyConfig {

//    @Bean
//    public LuaCheckTokenStrategy luaCheckTokenStrategy(StringRedisTemplate stringRedisTemplate){
//        return new LuaCheckTokenStrategy(stringRedisTemplate);
//    }
//
//    @Bean
//    public TokenStrategyContext tokenStrategyContext(CheckTokenStrategy checkTokenStrategy){
//        return new TokenStrategyContext(checkTokenStrategy);
//    }
}
