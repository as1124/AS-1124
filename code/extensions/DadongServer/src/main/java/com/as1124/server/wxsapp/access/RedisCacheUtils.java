package com.as1124.server.wxsapp.access;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

/**
 * Redis缓存处理工具类
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class RedisCacheUtils {

	private static RedisCacheUtils REDIS_SERVER = new RedisCacheUtils();

	public static void putKey(String key, String value) {

	}

	public static void putKey(String key, List<String> dataArray) {

	}

	public static void putKey(String key, Map<String, Object> dataMap) {

	}

	public static String getStringCache(String key) {
		return "";
	}

	public static List getListCache(String key) {
		return null;
	}

	public static Map getMapCache(String key) {
		return null;
	}

	public static void main(String[] args) {
		String redisHost = "127.0.0.1";
		int redisPort = 6379;
		Jedis redisClient = new Jedis(new HostAndPort(redisHost, redisPort));
		redisClient.connect();
		System.out.println("Redis 服务运行==" + redisClient.ping());

		System.out.println("获取缓存Key = " + redisClient.get("huangjiawei"));

		redisClient.close();
	}

}
