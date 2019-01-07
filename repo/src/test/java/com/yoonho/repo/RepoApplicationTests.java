package com.yoonho.repo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
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
	
	@Ignore
	@Test
	public void getJedis() throws Exception{
		Jedis jedis = redisConfig.getJedis();
		String secretKey = "test123";				// var : 사용자에게 입력받은 인증키
		
		String type_browser = "browser";			// var : browser type을 저장할 변수
		String type_mouse = "mouse";				// var : mouse type을 저장할 변수
		String type_keyboard = "keyboard";			// var : keyboard type을 저장할 변수
		
		String browserInfo_key = "browserInfo";		// var : browser type의 key를 저장할 변수
		String mouseInfo_key = "mouseInfo";			// var : mouse type의 key를 저장할 변수
		String keyboardInfo_key = "keyboardInfo";	// var : keyboard type의 key를 저장할 변수
		
		// {key: "사용자 인증키", field: "검색할 타입", value: "검색할 타입의 키값"}
		jedis.hset(secretKey, type_browser, browserInfo_key);		
		jedis.hset(secretKey, type_mouse, mouseInfo_key);
		jedis.hset(secretKey, type_keyboard, keyboardInfo_key);
		
		String browserInfo_value_ip = "127.0.0.1";
		String browserInfo_value_font = "font";
		
		// 사용자 인증키와 검색할 타입을 통해 특정 정보들을 저장
		String browserKey_fromJedis = jedis.hget(secretKey, type_browser);
		jedis.hset(browserKey_fromJedis, "ip", browserInfo_value_ip);
		jedis.hset(browserKey_fromJedis, "font", browserInfo_value_font);

//		System.out.println(jedis.hget(browserKey_fromJedis, "ip"));
//		System.out.println(jedis.hget(browserKey_fromJedis, "font"));
		
//		HashMap asix = new HashMap();
//		asix.put("x", "100");
//		asix.put("y", "150");
		
//		jedis.rpush("x", "1", "2");
		System.out.println(jedis.hgetAll(browserKey_fromJedis));
//		System.out.println(jedis.lpop("x"));
		
		
		
//		assertEquals(browserInfo_value_ip, jedis.hget(browserKey_fromJedis, "ip"));
//		assertEquals(browserInfo_value_font, jedis.hget(browserKey_fromJedis, "font"));
	}

	
	@Test
	public void redisAsixTest() throws Exception{
		Jedis jedis = redisConfig.getJedis();
		
		final int idx_x_asix = 0;
		final int idx_y_asix = 1; 
		
		String key = "test123";
		String type_browser = "browser";
		String type_browser_key = "browser_info";
		String type_mouse = "mouse";
		String type_mouse_key = "mouse_info";
		
		// 각 데이터 타입별 key 저장
		jedis.hset(key, type_browser, type_browser_key);
		jedis.hset(key, type_mouse, type_mouse_key);
		
		// Browser 사용자정보 저장
		String tmp_type_browser_key = jedis.hget(key, type_browser);
		jedis.hset(tmp_type_browser_key, "ip", "127.0.0.1");
		jedis.hset(tmp_type_browser_key, "font", "TitleFont");
		
		// Mouse 사용자입력정보 저장
		String tmp_type_mouse_key = jedis.hget(key, type_mouse);
		String key_mouseover[] = {"mouseover_x", "mouseover_y"};
		String key_mouseclick[] = {"mouseclick_x", "mouseclick_y"};
		String mouseover_x_value = "10 20 30 40 50";
		String mouseover_y_value = "100 200 300 400 500";
		String mouseclick_x_value = "10 20 30 40 50";
		String mouseclick_y_value = "100 200 300 400 500";
		
		jedis.hset(tmp_type_mouse_key, key_mouseover[idx_x_asix], mouseover_x_value);
		jedis.hset(tmp_type_mouse_key, key_mouseover[idx_y_asix], mouseover_y_value);
		jedis.hset(tmp_type_mouse_key, key_mouseclick[idx_x_asix], mouseclick_x_value);
		jedis.hset(tmp_type_mouse_key, key_mouseclick[idx_y_asix], mouseclick_y_value);
		
		// e.g. Mouse 사용자입력정보 중에서 mouseover값을 사용할때
		String tmp_mouse_key = jedis.hget(key, type_mouse);
		String tmp_mouseover_x_asix = jedis.hget(tmp_mouse_key, key_mouseover[idx_x_asix]);
		String tmp_mouseover_y_asix = jedis.hget(tmp_mouse_key, key_mouseover[idx_y_asix]);
		
		String test_x[] = tmp_mouseover_x_asix.split(" ");
		String test_y[] = tmp_mouseover_y_asix.split(" ");
		
		for(int i=0; i<test_x.length; i++) {
			System.out.println(test_x[i]);
			System.out.println(test_y[i]);
		}
		
		
		/*
		 * 마우스 사용자입력 정보의 각 타입별 asix를 x,y별로 띄어쓰기를 기준으로 하나의 String으로 받은 이유 
		 * : javascript에서 받은 x,y값들을 List 또는 HashMap 형태로 받아 일일히 redis에 저장하고 일일히 pop하는 것보다 
		 * javascript 내에서 String 형태로 묶어 이를 Controller로 보내고, Controller에서는 이를 그대로 Redis DB에 저장,
		 * 꺼내서 사용할때는 하나의 문자열을 가져와 띄어쓰기를 기준으로 split하는 방식이 훨씬 효율적이라는 생각으로 만듬 (redis.pop Resource양 > String split Resource양) 
		 */
	}
	
	
}

