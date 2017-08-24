package com.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/15.
 */
public class RedisTransfer {
    private static JedisPool jedisPool;
    private static Jedis jedis;
    private static JedisPool copyJedisPool;
    private static Jedis copyJedis;
    static {
        jedisPool = RedisUtil.jedisPool;
        jedis = RedisUtil.getJedis();
//        copyJedisPool = CopyRedisUtil.jedisPool;
//        copyJedis = CopyRedisUtil.getJedis();
    }

    /**
     *
     * @Title: get @Description: TODO(获取数据) @param: @param
     *         key @param: @return @return: String @throws
     */
    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }

    /**
     *
     * @Title: hget @Description: TODO(查询数据) @param: @param key @param: @param
     *         field @param: @return @return: String @throws
     */
    public static String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }

    /**
     *
     * @Title: hset @Description: TODO(设置从表数据) @param: @param key @param: @param
     *         field @param: @param value @param: @return @return: Long @throws
     */
    public static Long copyHset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = copyJedisPool.getResource();
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }
    /**
     *
     * @Title: backups
     * @Description: TODO(数据备份)
     * @param: @param file
     * @param: @param o
     * @return: void
     * @throws
     */
    public static void backups(String file, Object o) {
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(o.toString().getBytes());
            outStream.close();
            System.out.println("successful");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: dataTransfer @Description: TODO(数据迁移) @param:表名 @return:
     *         void @throws
     */
    public static void dataTransfer(String tableName) {
        Map<String, String> json = jedis.hgetAll(tableName); // 根据表名得到主表所有的数据
        backups("c:///"+tableName+".txt", json); //主表数据备份
        Map<String, String> copyJson = copyJedis.hgetAll(tableName); // 根据表名得到从表所有的数据
        backups("c:///Copy"+tableName+".txt", json); //从表数据备份
        copyJedis.del(tableName); //删除从表的数据
        for (Map.Entry<String, String> entry : json.entrySet()) { // 直接把主表的数据覆盖到从表
            copyHset(tableName, entry.getKey(), entry.getValue());
        }
    }

    public static void main(String[] args) {
        dataTransfer("account");

    }
}