package com.itheima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.ValidateCodeUtils;

import redis.clients.jedis.JedisPool;

/**
 *短信验证码 
 */ 
@RestController 
@RequestMapping("/validateCode")
public class ValidateCodeController {

	@Autowired 
	private JedisPool jedisPool;
	
}
