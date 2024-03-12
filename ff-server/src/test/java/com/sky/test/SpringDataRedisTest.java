package com.sky.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate(){
        System.out.println(redisTemplate);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    @Test
    public void testString(){
/*
        set get setex setnx
*/
        //set
        redisTemplate.opsForValue().set("city","北京");
        //get
        String city = (String) redisTemplate.opsForValue().get("city");
        System.out.println(city);
        //setex设置过期时间
        redisTemplate.opsForValue().set("code","1234",3,TimeUnit.MINUTES);
        //setnx
        redisTemplate.opsForValue().setIfAbsent("lock","1");
        redisTemplate.opsForValue().setIfAbsent("lock","2");
    }

    @Test
    public void testHash(){
/*
        hset hget hdel hkeys hvals
 */
        HashOperations hashOperations = redisTemplate.opsForHash();

        //hset
        hashOperations.put("100","name","wzp");
        hashOperations.put("100","age","21");

        //hget
        String name = (String) hashOperations.get("100", "name");
        System.out.println(name);

        //hkeys
        Set keys = hashOperations.keys("100");
        System.out.println(keys);

        //hvals
        List values = hashOperations.values("100");
        System.out.println(values);

        //hdel
        hashOperations.delete("100","age");

    }
    /**
     * 操作列表类型的数据
     */
    @Test
    public void testList(){
/*
        lpush lrange rpop llen
*/
        ListOperations listOperations = redisTemplate.opsForList();

        //lpush
        listOperations.leftPushAll("mylist","a","b","c");
        listOperations.leftPush("mylist","d");
        //lrange
        List mylist = listOperations.range("mylist", 0, -1);
        System.out.println(mylist);
        //rpop
        listOperations.rightPop("mylist");
        //llen
        Long size = listOperations.size("mylist");
        System.out.println(size);
    }

    /**
     * 操作集合类型的数据
     */
    @Test
    public void testSet(){
/*
        sadd smembers scard sinter sunion srem
*/
        SetOperations setOperations = redisTemplate.opsForSet();
        //sadd
        setOperations.add("set1","a","b","c","d");
        setOperations.add("set2","a","b","x","y");
        //smembers
        Set members = setOperations.members("set1");
        System.out.println(members);
        //scard
        Long size = setOperations.size("set1");
        System.out.println(size);
        //sinter
        Set intersect = setOperations.intersect("set1", "set2");
        System.out.println(intersect);
        //sunion
        Set union = setOperations.union("set1", "set2");
        System.out.println(union);
        //srem
        setOperations.remove("set1","a","b");
    }

    /**
     * 操作有序集合类型的数据
     */
    @Test
    public void testZset(){
/*
        zadd zrange zincrby zrem
*/
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        //zadd
        zSetOperations.add("zset1","a",10);
        zSetOperations.add("zset1","b",12);
        zSetOperations.add("zset1","c",9);
        //zrange
        Set zset1 = zSetOperations.range("zset1", 0, -1);
        System.out.println(zset1);
        //zincrby
        zSetOperations.incrementScore("zset1","c",10);
        //zrem
        zSetOperations.remove("zset1","a","b");
    }

    /**
     * 通用命令操作
     */
    @Test
    public void testCommon(){
        //keys exists type del
        Set keys = redisTemplate.keys("*");
        System.out.println(keys);

        Boolean name = redisTemplate.hasKey("name");
        Boolean set1 = redisTemplate.hasKey("set1");

        for (Object key : keys) {
            DataType type = redisTemplate.type(key);
            System.out.println(type.name());
        }

        redisTemplate.delete("mylist");
    }

}
