package com.yoonho.repo.config;

import org.springframework.context.annotation.Bean;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	
	@Bean(name="getJedis")
	public Jedis getJedis() throws Exception{
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
		Jedis jedis = jedisPool.getResource();
		
		return jedis;
	}
}
