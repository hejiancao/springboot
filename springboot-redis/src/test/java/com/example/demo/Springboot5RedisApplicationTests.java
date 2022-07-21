package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot5RedisApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private RedisDao redisDao;

	@Test
	public void test() {
		redisDao.setKey("hello", "world");
		System.out.println(redisDao.getValue("hello"));
	}

}
