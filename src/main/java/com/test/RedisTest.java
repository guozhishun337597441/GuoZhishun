package com.test;

import redis.clients.jedis.Jedis;

/**
 * @author gzs
 * @className:RedisTest
 * @Descirption :
 * @createDate 2019/6/20 09:47
 */
public class RedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.connect();
        jedis.set("user","zs");
        System.out.println(jedis.get("user"));

        jedis.append("user","ls");

        jedis.del("user");
        jedis.disconnect();

    }
}
