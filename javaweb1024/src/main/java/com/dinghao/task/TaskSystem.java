package com.dinghao.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dinghao.redis.template.RedisClientTemplate;
import com.dinghao.service.manage.AdminService;

@Component
public class TaskSystem {
	@Autowired
	protected RedisClientTemplate redisClientTemplate;
	
	/**
	 * 定时清理在线人数
	 */
	@Scheduled(cron="${alluser_number}")
	public void myTask(){
		redisClientTemplate.del("ALLUSER_NUMBER");
	}

}
