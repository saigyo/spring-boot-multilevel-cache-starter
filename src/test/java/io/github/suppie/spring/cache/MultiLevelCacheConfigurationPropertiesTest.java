package io.github.suppie.spring.cache;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

class MultiLevelCacheConfigurationPropertiesTest {

  static Stream<Arguments> useKeyPrefixAndKeySource() {
    return Stream.of(
        Arguments.of(true, "", "cachename::"),
        Arguments.of(true, "prefix-", "prefix-cachename::"),
        Arguments.of(false, "prefix-", "cachename::"));
  }

  @ParameterizedTest
  @MethodSource("useKeyPrefixAndKeySource")
  public void useKeyPrefixAndKeyPrefixAreSetOnRedisCacheConfiguration(
      boolean useKeyPrefix, String keyPrefix, String expectedPrefix) {
    MultiLevelCacheConfigurationProperties multiLevelCacheConfigurationProperties =
        new MultiLevelCacheConfigurationProperties();
    multiLevelCacheConfigurationProperties.setUseKeyPrefix(useKeyPrefix);
    multiLevelCacheConfigurationProperties.setKeyPrefix(keyPrefix);

    RedisCacheConfiguration configuration =
        multiLevelCacheConfigurationProperties.toRedisCacheConfiguration();

    // this is correct, because the "default configuration" always activates prefixing cache entry
    // key names with the cache name and '::'
    assertThat(configuration.usePrefix()).isEqualTo(true);
    assertThat(configuration.getKeyPrefixFor("cachename")).isEqualTo(expectedPrefix);
  }
}
