package edu.neu.cs.cs6650.redis;


import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
  private static final Logger logger = LogManager.getLogger(RedisPool.class.getName());
  private static boolean IS_LOCAL = true;
  private static final String HOST = "localhost";

  private static final JedisPoolConfig poolConfig = buildPoolConfig();
  private static final JedisPool REDIS_POOL = new JedisPool(poolConfig, HOST);

  private static JedisPoolConfig buildPoolConfig() {
    final JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(128);
    poolConfig.setMaxIdle(128);
    poolConfig.setMinIdle(16);
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setTestWhileIdle(true);
    poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
    poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
    poolConfig.setNumTestsPerEvictionRun(3);
    poolConfig.setBlockWhenExhausted(true);

    return poolConfig;
  }

  public static Jedis getResource() {
    return REDIS_POOL.getResource();
  }
}
