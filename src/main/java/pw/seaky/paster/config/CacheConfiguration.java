package pw.seaky.paster.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration


public class CacheConfiguration {


  @Autowired
  private Environment environment;


  @Bean(destroyMethod = "shutdown")
  RedissonClient redisson() {
    Config config = new Config();
    config.useSingleServer()
            .setAddress(environment.getProperty("redis.url"));
    config.setCodec(JsonJacksonCodec.INSTANCE);
    return Redisson.create(config);
  }

  @Bean
  CacheManager cacheManager(RedissonClient redissonClient) {
    Map<String, CacheConfig> config = new HashMap<>();
    config.put("pastes", new CacheConfig(60 * 60 * 1000, 12 * 60 * 1000));
    return new RedissonSpringCacheManager(redissonClient, config);
  }


}