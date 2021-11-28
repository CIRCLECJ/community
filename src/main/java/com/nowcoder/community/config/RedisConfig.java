package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean//定义第三方bean。你要把哪个对象装配到容器当中就返回哪个对象。
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        //连接是由连接工厂创建的因此要注入连接工厂。当你在定义一个bean的时候在参数里加这样，spring会自动把这个bean注入进来，这个bean就已经被容器装配了

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);//这样它就具备了访问数据库的能力

        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());//RedisSerializer.string()方法返回一个能够序列化字符串的序列化器
        // 设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        // 设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());
        //触发生效
        template.afterPropertiesSet();
        return template;
    }

}
