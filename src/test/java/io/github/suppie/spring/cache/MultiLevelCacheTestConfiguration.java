package io.github.suppie.spring.cache;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class MultiLevelCacheTestConfiguration {
  @Bean
  public RedisConnection mockRedisConnection() {
    return mock(RedisConnection.class);
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory(RedisConnection mockRedisConnection) {
    RedisConnectionFactory mockRedisConnectionFactory = mock(RedisConnectionFactory.class);
    when(mockRedisConnectionFactory.getConnection()).thenReturn(mockRedisConnection);
    return mockRedisConnectionFactory;
  }

  @Bean
  public Supplier<RedisMessageListenerContainer> redisMessageListenerContainerSupplier() {
    return () -> mock(RedisMessageListenerContainer.class);
  }
}
