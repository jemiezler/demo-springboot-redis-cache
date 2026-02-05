package com.example.redis_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisApiApplication.class, args);
	}

}
