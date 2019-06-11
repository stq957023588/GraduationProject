package com.slz.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
@Configuration
@EnableCaching
@Slf4j
public class RedisCacheConfig extends CachingConfigurerSupport {


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, String> redisTemplate=new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(factory);


        RedisSerializer<String> redisSerializer=new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        return redisTemplate;
    }


    @Override
    public CacheErrorHandler errorHandler(){
        CacheErrorHandler cacheErrorHandler=new CacheErrorHandler() {

            @Override
            public void handleCachePutError(RuntimeException e, Cache arg1, Object arg2, Object arg3) {
                // TODO Auto-generated method stub
                RedisErrorExceptionLog(e,arg2,arg1);
            }

            @Override
            public void handleCacheGetError(RuntimeException e, Cache arg1,Object arg2) {
                // TODO Auto-generated method stub
                RedisErrorExceptionLog(e,arg2,arg1);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache arg1,Object arg2) {
                // TODO Auto-generated method stub
                RedisErrorExceptionLog(e,arg2,arg1);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache arg1) {
                // TODO Auto-generated method stub
                RedisErrorExceptionLog(e,null,arg1);
            }
        };
        return cacheErrorHandler;
    }
    protected void RedisErrorExceptionLog(Exception e,Object key,Cache cache){
        log.error("redis异常: cache=[{}],key=[{}],exception={}",cache.getName(),key,e.getMessage());
    }
}
