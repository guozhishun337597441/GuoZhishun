package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author gzs
 * @className:RedisTestTest
 * @Descirption :
 * @createDate 2019/6/20 09:55
 */
public class RedisTestTest {
    private Jedis jedis;
    @Before
    public void init(){
        jedis = new Jedis("127.0.0.1",6379);
        jedis.connect();
    }

    @Test
    public void testString(){
        jedis.set("name","gzs");
        System.out.println("拼接前:"+jedis.get("name"));

        jedis.append("name"," is my name");
        System.out.println("拼接后:"+jedis.get("name"));

        jedis.del("name");
        System.out.println("删除后:"+jedis.get("name"));

        jedis.mset("name","gzs","age","23","sex","man");
        jedis.incr("age");
        jedis.decr("age");
        System.out.println("年龄是"+jedis.get("age"));
    }

    @Test
    public void testMap(){
        //添加数据
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "chx");
        map.put("age", "100");
        map.put("email", "***@outlook.com");
        jedis.hmset("user", map);
        //取出user中的name，结果是一个泛型的List
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key是可变参数
        List<String> list = jedis.hmget("user", "name", "age", "email");
        System.out.println(list);

        //删除map中的某个键值
        jedis.hdel("user", "age");
        System.out.println("age:" + jedis.hmget("user", "age")); //因为删除了，所以返回的是null
        System.out.println("user的键中存放的值的个数:" + jedis.hlen("user")); //返回key为user的键中存放的值的个数2
        System.out.println("是否存在key为user的记录:" + jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println("user对象中的所有key:" + jedis.hkeys("user"));//返回user对象中的所有key
        System.out.println("user对象中的所有value:" + jedis.hvals("user"));//返回map对象中的所有value

        //拿到key，再通过迭代器得到值
        Iterator<String> iterator = jedis.hkeys("user").iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
        jedis.del("user");
        System.out.println("删除后是否存在key为user的记录:" + jedis.exists("user"));//是否存在key为user的记录

    }

    @Test
    public void testList(){
        jedis.del("javaFramework");

        jedis.lpush("javaFramework","spring");
        jedis.lpush("javaFramework","springMvc");
        jedis.lpush("javaFramework","mybatis");

        System.out.println("长度:"+jedis.llen("javaFramework"));
        System.out.println("javaFramework:"+jedis.lrange("javaFramework",0,-1));
        jedis.del("javaFramework");
        System.out.println("删除后长度"+jedis.llen("javaFramework"));
        System.out.println(jedis.lrange("javaFramework",0,-1));
    }

    @Test
    public void testSet(){
        jedis.sadd("user","zs");
        jedis.sadd("user","ls");
        jedis.sadd("user","ww");
        jedis.sadd("user","hh");
        jedis.srem("user","hh");
        System.out.println("user的value:"+jedis.smembers("user"));
        System.out.println("hh是否是user的元素:"+jedis.sismember("user","hh"));
        System.out.println("集合中的一个随机元素:"+jedis.srandmember("user"));
        System.out.println("user中元素的个数:"+jedis.scard("user"));
    }

    @Test
    public void testSort(){
        jedis.del("number");
        jedis.rpush("number","4");
        jedis.rpush("number","5");
        jedis.rpush("number","3");

        jedis.lpush("number","9");
        jedis.rpush("number","12");
        System.out.println(jedis.lrange("number",0,jedis.llen("number")));
        System.out.println("排序:"+jedis.sort("number"));
        System.out.println(jedis.lrange("number",0,-1));
        jedis.del("number");
    }

    @After
    public void close(){
        jedis.disconnect();
    }

}