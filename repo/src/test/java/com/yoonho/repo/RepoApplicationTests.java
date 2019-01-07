package com.yoonho.repo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yoonho.repo.config.*;

import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepoApplicationTests {

	@Autowired
	RedisConfig redisConfig;
	
	@Test
	public void getJedis() throws Exception{
		Jedis jedis = redisConfig.getJedis();
		String secretKey = "test123";
		
		String browserInfo_field = "browserInfo";
		String mouseInfo_field = "mouseInfo";
		String keyboardInfo_field = "keyboardInfo";
		
		String browserinfo_value = "browserInfo_value";
		String mouseInfo_value = "mouseInfo_value";
		String keyboardinfo_value = "keyboardInfo_value";
		
		jedis.hset(secretKey, browserInfo_field, browserinfo_value);
		jedis.hset(secretKey, mouseInfo_field, mouseInfo_value);
		jedis.hset(secretKey, keyboardInfo_field, keyboardinfo_value);
		
		assertEquals(browserinfo_value, jedis.hget(secretKey, browserInfo_field));
		assertEquals(mouseInfo_value, jedis.hget(secretKey, mouseInfo_field));
		assertEquals(keyboardinfo_value, jedis.hget(secretKey, keyboardInfo_field));
	}

}

