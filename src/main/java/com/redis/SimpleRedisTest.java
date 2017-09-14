package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/8/14.
 */
public class SimpleRedisTest {

    String fileName = "D://out//RedisBackup-" + currentTime() + ".txt";
    File file = new File(fileName);

    public void testRedis() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        Jedis jedis = new Jedis("200.200.18.129", 6379);
//        jedis.sadd("123456","");

//        System.out.println(jedis.randomKey());

        //获取key对应的数据类型
//        System.out.println(jedis.type("uscore_4384935_000000"));
//        System.out.println(jedis.hget("uscore_4384935_000000","current_score"));

        //获取redis中最大值对应的元素
        Set sets = jedis.zrevrangeByScore("223456789","+inf","-inf",0,2);
        Iterator<String> itSets = sets.iterator();
        while (itSets.hasNext()) {
            String firstItem = itSets.next();
            if(!"time".endsWith(firstItem)){    //用来判断成员是否是time
                System.out.println(firstItem);
                break;
            }
//            System.out.println(firstItem);
        }

//        System.out.println(jedis.zincrby("2222",2,"test11"));
        Set<Tuple> tuples = jedis.zrevrangeWithScores("1111",0,-1);

        for(Tuple t : tuples){
            System.out.println(t.getElement() + ":" + t.getScore());
        }

//        jedis.zadd("1111",22,"test1");

        //获取元素的得分
//        System.out.println(jedis.zscore("1111","test2"));
/*
        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        System.out.println(keys.size());
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            //获得key值
            String key = it.next();
            //获得value值
            Map<String, String> value = jedis.hgetAll(key);
            backupRedisData(key, value);
        }*/
        jedis.close();
    }


    /**
     * 备份redis数据
     *
     * @param key
     * @param value
     */
    public void backupRedisData(String key, Map value) throws IOException {


        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bufferWritter = new BufferedWriter(fw);
        bufferWritter.write(key + ":" + value);
        bufferWritter.newLine();
        bufferWritter.close();
        fw.close();
    }

    //获取当前系统时间
    public String currentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(new Date());//设置当前日期
//        System.out.println(sdf.format(calendar.getTime()));
        return sdf.format(calendar.getTime());
    }

    public static void main(String[] args) throws IOException {
        SimpleRedisTest srt = new SimpleRedisTest();
        srt.testRedis();
    }
}
