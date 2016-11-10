/**
 * 
 */
package com.dinghao.plugin;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.dinghao.constant.Constants;

/**
 * Redis缓存配置
 * 
 * @author zihan1120
 * @version 
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			/** 重写生成key方法 */
			public Object generate(Object o, Method method, Object... objects) {
				StringBuilder sb = new StringBuilder(Constants.CACHE_NAMESPACE);
				sb.append(o.getClass().getName()).append(":");
				CacheConfig cacheConfig = o.getClass().getAnnotation(CacheConfig.class);
				Cacheable cacheable = method.getAnnotation(Cacheable.class);
				CachePut cachePut = method.getAnnotation(CachePut.class);
				CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
				if (cacheable != null) {
					String[] cacheNames = cacheable.value();
					if (cacheNames != null && cacheNames.length > 0) {
						sb.append(cacheNames[0].toString());
					}
				} else if (cachePut != null) {
					String[] cacheNames = cachePut.value();
					if (cacheNames != null && cacheNames.length > 0) {
						sb.append(cacheNames[0].toString());
					}
				} else if (cacheEvict != null) {
					String[] cacheNames = cacheEvict.value();
					if (cacheNames != null && cacheNames.length > 0) {
						sb.append(cacheNames[0].toString());
					}
				}
				if (cacheConfig != null && sb.toString().equals(Constants.CACHE_NAMESPACE)) {
					String[] cacheNames = cacheConfig.cacheNames();
					if (cacheNames != null && cacheNames.length > 0) {
						sb.append(cacheNames[0].toString());
					}
				}
				if (sb.toString().equals(Constants.CACHE_NAMESPACE)) {
					sb.append(o.getClass().getName());
				}
				sb.append(":");
				if (objects != null) {
					if (objects.length == 1) {
						if (objects[0] instanceof Number || objects[0] instanceof String
								|| objects[0] instanceof Boolean) {
							sb.append(objects[0].toString());
						} else {
							try {
								sb.append(objects[0].getClass().getMethod("getId").invoke(objects[0]));
							} catch (Exception e) {
								if (objects[0] instanceof Map && ((Map<?, ?>) objects[0]).get("id") != null) {
									sb.append(((Map<?, ?>) objects[0]).get("id"));
								} else {
									sb.append(JSON.toJSON(objects[0]));
								}
							}
						}
					} else {
						sb.append(StringUtils.join(objects, ","));
					}
				} else {
					sb.append(method.getName());
				}
				return sb.toString();
			}
		};
	}
}
