package com.redis.program;

import redis.clients.jedis.*;

import java.util.*;

public class RedisManager {

	private RedisConnectFactory redisConnectFactory;

	public Jedis getJedis() {
		return redisConnectFactory.getInstance();
	}

	public void releaseJedis(Jedis jedis) {
		redisConnectFactory.returnResource(jedis);
	}

	public void releaseBrokenJedis(Jedis jedis) {
//		redisConnectFactory.returnBrokenResource(jedis);
	}

	public String auth(String password) {
		Jedis jedis = getJedis();
		String result = jedis.auth(password);
		releaseJedis(jedis);
		return result;
	}

	public String ping() {
		Jedis jedis = getJedis();
		String result = jedis.ping();
		releaseJedis(jedis);
		return result;
	}

	public String select(int index) {
		Jedis jedis = getJedis();
		String result = jedis.select(index);
		releaseJedis(jedis);
		return result;
	}

	public String echo(String string) {
		Jedis jedis = getJedis();
		String result = jedis.echo(string);
		releaseJedis(jedis);
		return result;
	}

	public String quit() {
		Jedis jedis = getJedis();
		String result = jedis.quit();
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key给field设置指定的值,如果key不存在,则先创建
	 * </p>
	 * 
	 * @param key
	 * @param field
	 *            字段
	 * @param value
	 * @return 如果存在返回0 异常返回null
	 */
	public Long hset(String key, String field, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.hset(key, field, value);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hsetnx(String key, String field, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.hsetnx(key, field, value);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key同时设置 hash的多个field
	 * </p>
	 * 
	 * @param key
	 * @param hash
	 * @return 返回OK 异常返回null
	 */
	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = getJedis();
		String result = jedis.hmset(key, hash);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key 和 field 获取指定的 value
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @return 没有返回null
	 */
	public String hget(String key, String field) {
		Jedis jedis = getJedis();
		String result = jedis.hget(key, field);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key 和 fields 获取指定的value 如果没有对应的value则返回null
	 * </p>
	 * 
	 * @param key
	 * @param fields
	 *            可以使 一个String 也可以是 String数组
	 * @return
	 */
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = getJedis();
		List<String> result = jedis.hmget(key, fields);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key获取所有的field和value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		Jedis jedis = getJedis();
		Map<String, String> result = jedis.hgetAll(key);
		releaseJedis(jedis);
		return result;
	}

	public Long hdel(String key, String field) {
		Jedis jedis = getJedis();
		Long result = jedis.hdel(key, field);
		releaseJedis(jedis);
		return result;
	}

	public Long hlen(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.hlen(key);
		releaseJedis(jedis);
		return result;
	}

	public Boolean hexists(String key, String field) {
		Jedis jedis = getJedis();
		Boolean result = jedis.hexists(key, field);
		releaseJedis(jedis);
		return result;
	}


	public Set<String> hkeys(String key) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.hkeys(key);
		releaseJedis(jedis);
		return result;
	}

	public List<String> hvals(String key) {
		Jedis jedis = getJedis();
		List<String> result = jedis.hvals(key);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 判断key是否存在
	 * </p>
	 * 
	 * @param key
	 * @return true OR false
	 */
	public boolean exists(String key) {
		Jedis jedis = null;
		boolean result = false;
		try {
			jedis = getJedis();
			result = jedis.exists(key);
		} catch (Exception e) {
			releaseBrokenJedis(jedis);
			e.printStackTrace();
		} finally {
			releaseJedis(jedis);
		}

		return result;
	}

	/**
	 * <p>
	 * 删除指定的key,也可以传入一个包含key的数组
	 * </p>
	 * 
	 * @param keys
	 *            一个key 也可以使 string 数组
	 * @return 返回删除成功的个数
	 */
	public Long del(String... keys) {
		Jedis jedis = null;
		Long result = 0L;
		try {
			jedis = getJedis();
			result = jedis.del(keys);
		} catch (Exception e) {
			releaseBrokenJedis(jedis);
			e.printStackTrace();
			result = 0L;
		} finally {
			releaseJedis(jedis);
		}
		return result;
	}

	public String randomKey() {
		Jedis jedis = getJedis();
		String result = jedis.randomKey();
		releaseJedis(jedis);
		return result;
	}

	public String rename(String oldkey, String newkey) {
		Jedis jedis = getJedis();
		String result = jedis.rename(oldkey, newkey);
		releaseJedis(jedis);
		return result;
	}

	public Long dbsize() {
		Jedis jedis = getJedis();
		Long result = jedis.dbSize();
		releaseJedis(jedis);
		return result;
	}

	public String fhushAll() {
		Jedis jedis = getJedis();
		String result = jedis.flushAll();
		releaseJedis(jedis);
		return result;
	}

	public List<String> sort(String key) {
		Jedis jedis = getJedis();
		List<String> result = jedis.sort(key);
		releaseJedis(jedis);
		return result;
	}

	public Long move(String key, int dbIndex) {
		Jedis jedis = getJedis();
		Long result = jedis.move(key, dbIndex);
		releaseJedis(jedis);
		return result;
	}

	public Long renamenx(String oldKey, String newKey) {
		Jedis jedis = getJedis();
		Long result = jedis.renamenx(oldKey, newKey);
		releaseJedis(jedis);
		return result;
	}

	public String type(String key) {
		Jedis jedis = getJedis();
		String result = jedis.type(key);
		releaseJedis(jedis);
		return result;
	}

	public Long expire(String key, int seconds) {
		Jedis jedis = getJedis();
		Long result = jedis.expire(key, seconds);
		releaseJedis(jedis);
		return result;
	}
	
	public Long pexpire(String key, int us) {
		Jedis jedis = getJedis();
		Long result = jedis.pexpire(key, us);
		releaseJedis(jedis);
		return result;
	}

	public Long expireAt(String key, Long unixTime) {
		Jedis jedis = getJedis();
		Long result = jedis.expireAt(key, unixTime);
		releaseJedis(jedis);
		return result;
	}

	public Long lpush(String key, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.lpush(key, value);
		releaseJedis(jedis);
		return result;
	}

	public Long rpush(String key, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.rpush(key, value);
		releaseJedis(jedis);
		return result;
	}

	public Long llen(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.llen(key);
		releaseJedis(jedis);
		return result;
	}

	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = getJedis();
		List<String> result = jedis.lrange(key, start, end);
		releaseJedis(jedis);
		return result;
	}

	public String ltrim(String key, long start, long end) {
		Jedis jedis = getJedis();
		String result = jedis.ltrim(key, start, end);
		releaseJedis(jedis);
		return result;
	}

	public Long lpushx(String key, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.lpushx(key, value);
		releaseJedis(jedis);
		return result;
	}

	public Long rpushx(String key, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.rpushx(key, value);
		releaseJedis(jedis);
		return result;
	}

	public String lpop(String key) {
		Jedis jedis = getJedis();
		String result = jedis.lpop(key);
		releaseJedis(jedis);
		return result;
	}

	public String rpop(String key) {
		Jedis jedis = getJedis();
		String result = jedis.rpop(key);
		releaseJedis(jedis);
		return result;
	}

	public Long lrem(String key, long count, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.lrem(key, count, value);
		releaseJedis(jedis);
		return result;
	}

	public String lset(String key, long index, String value) {
		Jedis jedis = getJedis();
		String result = jedis.lset(key, index, value);
		releaseJedis(jedis);
		return result;
	}

	public String lindex(String key, long index) {
		Jedis jedis = getJedis();
		String result = jedis.lindex(key, index);
		releaseJedis(jedis);
		return result;
	}

	public String rpoplpush(String srcKey, String dstKey) {
		Jedis jedis = getJedis();
		String result = jedis.rpoplpush(srcKey, dstKey);
		releaseJedis(jedis);
		return result;
	}

	public String brpoplpush(String source, String destination, int timeout) {
		Jedis jedis = getJedis();
		String result = jedis.brpoplpush(source, destination, timeout);
		releaseJedis(jedis);
		return result;
	}

	public Long publish(String channel, String message) {
		Jedis jedis = getJedis();
		Long result = jedis.publish(channel, message);
		releaseJedis(jedis);
		return result;
	}

	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		Jedis jedis = getJedis();
		jedis.subscribe(jedisPubSub, channels);
		releaseJedis(jedis);
	}

	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		Jedis jedis = getJedis();
		jedis.psubscribe(jedisPubSub, patterns);
		releaseJedis(jedis);
	}

	public Long sadd(String key, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.sadd(key, member);
		releaseJedis(jedis);
		return result;
	}

	public Long srem(String key, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.srem(key, member);
		releaseJedis(jedis);
		return result;
	}

	public Set<String> smembers(String key) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.smembers(key);
		releaseJedis(jedis);
		return result;
	}

	public Boolean sismember(String key, String member) {
		Jedis jedis = getJedis();
		Boolean result = jedis.sismember(key, member);
		releaseJedis(jedis);
		return result;
	}

	public Long scard(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.scard(key);
		releaseJedis(jedis);
		return result;
	}

	public Long smove(String srckey, String dstkey, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.smove(srckey, dstkey, member);
		releaseJedis(jedis);
		return result;
	}

	public String spop(String key) {
		Jedis jedis = getJedis();
		String result = jedis.spop(key);
		releaseJedis(jedis);
		return result;
	}

	public String srandmember(String key) {
		Jedis jedis = getJedis();
		String result = jedis.srandmember(key);
		releaseJedis(jedis);
		return result;
	}

	public Set<String> sinter(String... keys) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.sinter(keys);
		releaseJedis(jedis);
		return result;
	}

	public Long sinterstore(String dstkey, String... keys) {
		Jedis jedis = getJedis();
		Long result = jedis.sinterstore(dstkey, keys);
		releaseJedis(jedis);
		return result;
	}

	public Set<String> sunion(String... keys) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.sunion(keys);
		releaseJedis(jedis);
		return result;
	}

	public Long sunionstore(String dstkey, String... keys) {
		Jedis jedis = getJedis();
		Long result = jedis.sunionstore(dstkey, keys);
		releaseJedis(jedis);
		return result;
	}

	public Set<String> sdiff(String... keys) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.sdiff(keys);
		releaseJedis(jedis);
		return result;
	}

	public Long sdiffstore(String dstkey, String... keys) {
		Jedis jedis = getJedis();
		Long result = jedis.sdiffstore(dstkey, keys);
		releaseJedis(jedis);
		return result;
	}

	public String bgrewriteaof() {
		Jedis jedis = getJedis();
		String result = jedis.bgrewriteaof();
		releaseJedis(jedis);
		return result;
	}

	public String bgsave() {
		Jedis jedis = getJedis();
		String result = jedis.bgsave();
		releaseJedis(jedis);
		return result;
	}

	public Long lastsave() {
		Jedis jedis = getJedis();
		Long result = jedis.lastsave();
		releaseJedis(jedis);
		return result;
	}

	public String slaveof(String host, int port) {
		Jedis jedis = getJedis();
		String result = jedis.slaveof(host, port);
		releaseJedis(jedis);
		return result;
	}

	public String flushAll() {
		Jedis jedis = getJedis();
		String result = jedis.flushAll();
		releaseJedis(jedis);
		return result;
	}

	public String flushDB() {
		Jedis jedis = getJedis();
		String result = jedis.flushDB();
		releaseJedis(jedis);
		return result;
	}

	public String shutdown() {
		Jedis jedis = getJedis();
		String result = jedis.shutdown();
		releaseJedis(jedis);
		return result;
	}

	public String info() {
		Jedis jedis = getJedis();
		String result = jedis.info();
		releaseJedis(jedis);
		return result;
	}

	public List<String> configGet(String pattern) {
		Jedis jedis = getJedis();
		List<String> result = jedis.configGet(pattern);
		releaseJedis(jedis);
		return result;
	}

	public String configSet(String parameter, String value) {
		Jedis jedis = getJedis();
		String result = jedis.configSet(parameter, value);
		releaseJedis(jedis);
		return result;
	}

	public String configResetStat() {
		Jedis jedis = getJedis();
		String result = jedis.configResetStat();
		releaseJedis(jedis);
		return result;
	}

	public String debug(DebugParams params) {
		Jedis jedis = getJedis();
		String result = jedis.debug(params);
		releaseJedis(jedis);
		return result;
	}

	public void monitor(JedisMonitor jedisMonitor) {
		Jedis jedis = getJedis();
		jedis.monitor(jedisMonitor);
		releaseJedis(jedis);
	}

	public void sync() {
		Jedis jedis = getJedis();
		jedis.sync();
		releaseJedis(jedis);
	}

	public Long zadd(String key, double score, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.zadd(key, score, member);
		releaseJedis(jedis);
		return result;
	}
	
	/*public void zadd(String key, List<ScoreMember> sms) {
		Jedis jedis=getJedis();
		try{
			for(ScoreMember sm : sms){
				jedis.zadd(key, sm.getScore(), sm.getMember());
			}
		}finally{
			releaseJedis(jedis);
		}
	}*/

	public Long zrem(String key, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.zrem(key, member);
		releaseJedis(jedis);
		return result;
	}

	public Long zcard(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.zcard(key);
		releaseJedis(jedis);
		return result;
	}

	public Long zcount(String key, double min, double max) {
		Jedis jedis = getJedis();
		Long result = jedis.zcount(key, min, max);
		releaseJedis(jedis);
		return result;
	}

	public Double zscore(String key, String member) {
		Jedis jedis = getJedis();
		Double result = jedis.zscore(key, member);
		releaseJedis(jedis);
		return result;
	}

	public Double zincrby(String key, double score, String member) {
		Jedis jedis = getJedis();
		Double result = jedis.zincrby(key, score, member);
		releaseJedis(jedis);
		return result;
	}

	public Set<String> zrange(String key, int start, int end) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrange(key, start, end);
		releaseJedis(jedis);
		return result;
	}

	public Set<String> zrevrange(String key, int start, int end) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrevrange(key, start, end);
		releaseJedis(jedis);
		return result;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min,int offset, int count) {
		Jedis jedis=getJedis();
		try{
			Set<String> result=jedis.zrevrangeByScore(key, max, min, offset, count);
			return result;
		}finally{
			releaseJedis(jedis);
		}
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		Jedis jedis=getJedis();
		try{
			Set<String> result=jedis.zrevrangeByScore(key, max, min);
			return result;
		}finally{
			releaseJedis(jedis);
		}
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		Jedis jedis=getJedis();
		try{
			Set<String> result=jedis.zrangeByScore(key, min, max);
			return result;
		}finally{
			releaseJedis(jedis);
		}
	}
	public Long zrank(String key, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.zrank(key, member);
		releaseJedis(jedis);
		return result;
	}

	public Long zrevrank(String key, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.zrevrank(key, member);
		releaseJedis(jedis);
		return result;
	}

	public Long zremrangeByRank(String key, int start, int end) {
		Jedis jedis = getJedis();
		Long result = jedis.zremrangeByRank(key, start, end);
		releaseJedis(jedis);
		return result;
	}

	public Long zremrangeByScore(String key, double start, double end) {
		Jedis jedis = getJedis();
		Long result = jedis.zremrangeByScore(key, start, end);
		releaseJedis(jedis);
		return result;
	}

	public Long zinterstore(String dstkey, String... sets) {
		Jedis jedis = getJedis();
		Long result = jedis.zinterstore(dstkey, sets);
		releaseJedis(jedis);
		return result;
	}

	public Long zunionstore(String dstkey, String... sets) {
		Jedis jedis = getJedis();
		Long result = jedis.zunionstore(dstkey, sets);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key获取储存在redis中的value
	 * </p>
	 * <p>
	 * 并释放连接
	 * </p>
	 * 
	 * @param key
	 * @return 成功返回value 失败返回null
	 */
	public String get(String key) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();
			result = jedis.get(key);
		} catch (Exception e) {
			releaseBrokenJedis(jedis);
			e.printStackTrace();
		} finally {
			releaseJedis(jedis);
		}
		return result;
	}

	/**
	 * <p>
	 * 向redis存入key和value,并释放连接资源
	 * </p>
	 * <p>
	 * 如果key已经存在 则覆盖
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return 成功 返回OK 失败返回 0
	 */
	public String set(String key, String value) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();
			result = jedis.set(key, value);
		} catch (Exception e) {
			releaseBrokenJedis(jedis);
			e.printStackTrace();
			result = "0";
		} finally {
			releaseJedis(jedis);
		}
		return result;
	}

	/**
	 * <p>
	 * 设置key value,如果key已经存在则返回0,nx==> not exist
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return 成功返回1 如果存在 和 发生异常 返回 0
	 */
	public Long setnx(String key, String value) {
		Jedis jedis = null;
		Long result = 0L;
		try {
			jedis = getJedis();
			result = jedis.setnx(key, value);
		} catch (Exception e) {
			releaseBrokenJedis(jedis);
			e.printStackTrace();
		} finally {
			releaseJedis(jedis);
		}

		return result;
	}

	public String getSet(String key, String value) {
		Jedis jedis = getJedis();
		String result = jedis.getSet(key, value);
		releaseJedis(jedis);
		return result;
	}

	public List<String> mget(String[] keys) {
		Jedis jedis = getJedis();
		List<String> result = jedis.mget(keys);
		releaseJedis(jedis);
		return result;
	}

	public void mset(String... keysvalues) {
		Jedis jedis = getJedis();
		jedis.mset(keysvalues);
		releaseJedis(jedis);
	}

	public void msetnx(String... keysvalues) {
		Jedis jedis = getJedis();
		jedis.msetnx(keysvalues);
		releaseJedis(jedis);
	}

	public Long incrBy(String key, Integer integer) {
		Jedis jedis = getJedis();
		Long result = jedis.incrBy(key, integer);
		releaseJedis(jedis);
		return result;
	}

	public Long strlen(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.strlen(key);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
	 * </p>
	 * 
	 * @param key
	 * @return 加值后的结果
	 */
	public Long incr(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.incr(key);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key给指定的field的value加上给定的值
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hincrBy(String key, String field, long value) {
		Jedis jedis = getJedis();
		Long result = jedis.hincrBy(key, field, value);
		releaseJedis(jedis);
		return result;
	}
	
	/**
	 * <p>
	 * 对key的值做减减操作,如果key不存在,则设置key为-1
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.decr(key);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 减去指定的值
	 * </p>
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long decrBy(String key, Integer integer) {
		Jedis jedis = getJedis();
		Long result = jedis.decrBy(key, integer);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 通过key向指定的value值追加值
	 * </p>
	 * 
	 * @param key
	 * @param str
	 * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度 异常返回0L
	 */
	public Long append(String key, String str) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = getJedis();
			res = jedis.append(key, str);
		} catch (Exception e) {
			releaseBrokenJedis(jedis);
			e.printStackTrace();
			res = 0L;
		} finally {
			releaseJedis(jedis);
		}
		return res;
	}

	public String subStr(String key, int Start, int end) {
		Jedis jedis = getJedis();
		String result = jedis.substr(key, Start, end);
		releaseJedis(jedis);
		return result;
	}

	/**
	 * <p>
	 * 设置key value并制定这个键值的有效期
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            单位:秒
	 * @return 成功返回OK 失败和异常返回null
	 */
	public String setex(String key, String value, int seconds) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = getJedis();
			res = jedis.setex(key, seconds, value);
		} catch (Exception e) {
			releaseBrokenJedis(jedis);
			e.printStackTrace();
		} finally {
			releaseJedis(jedis);
		}
		return res;
	}

	public Long setRange(String key, long offset, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.setrange(key, offset, value);
		releaseJedis(jedis);
		return result;
	}

	public String getRange(String key, long StartOffset, long endOffset) {
		Jedis jedis = getJedis();
		String result = jedis.getrange(key, StartOffset, endOffset);
		releaseJedis(jedis);
		return result;
	}

	public String watch(String... keys) {
		Jedis jedis = getJedis();
		String result = jedis.watch(keys);
		releaseJedis(jedis);
		return result;
	}

	public String unwatch() {
		Jedis jedis = getJedis();
		String result = jedis.unwatch();
		releaseJedis(jedis);
		return result;
	}

	public void multi() {
		Jedis jedis = getJedis();
		jedis.multi();
		releaseJedis(jedis);
	}

	public Set<String> keys(String key){
		Jedis jedis = getJedis();
		Set<String> keys = jedis.keys(key);
		releaseJedis(jedis);
		return keys;
	}

	public List<String> sort(String key,SortingParams params){
		Jedis jedis = getJedis();
		List<String> sortedResult =  jedis.sort(key,params);
		releaseJedis(jedis);
		return sortedResult;
	}

	/**
	 * 检测给定key的剩余生存时间，单位秒
	 * @param key
	 * @return returns -2 if the key does not exist.returns -1 if the key exists but has no associated expire.
     */
	public long ttl(String key){
		Long result = -1L;
		Jedis jedis = null;
		try{
			jedis = getJedis();
			result = jedis.ttl(key);
		}catch (Exception e){
			if(jedis != null){
				redisConnectFactory.returnBrokenResource(jedis);
			}
		}finally {
			redisConnectFactory.returnResource(jedis);
		}

		return result;
	}

	/**
	 * 检测给定key的剩余生存时间，单位毫秒
	 * @param key
	 * @return returns -2 if the key does not exist.returns -1 if the key exists but has no associated expire.
     */
	public long pttl(String key){
		long result = -2L;
		Jedis jedis = null;
		try{
			jedis = getJedis();
			result = jedis.pttl(key);
		}catch (Exception e){
			if(jedis != null){
				redisConnectFactory.returnBrokenResource(jedis);
			}
		}finally {
			redisConnectFactory.returnResource(jedis);
		}
		return result;
	}

	public ScanResult<Map.Entry<String,String>> hscan(String key,String cursor){
		Jedis jedis = null;
		ScanResult<Map.Entry<String,String>> scanResult = null;
		try{
			jedis = getJedis();
			scanResult = jedis.hscan(key,cursor);
		}catch (Exception e){
			if(jedis != null){
				redisConnectFactory.returnBrokenResource(jedis);
			}
		}finally {
			redisConnectFactory.returnResource(jedis);
		}
		return scanResult;
	}

	public Map<String,String> hscanAll(String key){
		Map<String, String> mapResult = new HashMap<String, String>();
		ScanResult<Map.Entry<String,String>> scanResult = null;
		Jedis jedis = null;
		try{
			jedis = getJedis();
			String cursor = "0";
			do{
				scanResult = jedis.hscan(key,cursor);
				cursor = scanResult.getStringCursor();
				List<Map.Entry<String,String>> results =  scanResult.getResult();
				for(Map.Entry<String,String> result : results){
					mapResult.put(result.getKey(),result.getValue());
				}
			}while (!"0".equals(cursor));
		}catch (Exception e){
			if(jedis != null){
				redisConnectFactory.returnBrokenResource(jedis);
			}
		}finally {
			redisConnectFactory.returnResource(jedis);
		}
		return  mapResult;
	}

	public List<String> sscan(String key){
		List<String> resultList = new ArrayList<String>();
		ScanResult<String> scanResult = null;
		Jedis jedis = null;
		try{
			jedis = getJedis();
			String cursor = "0";
			do{
				scanResult = jedis.sscan(key,cursor);
				cursor = scanResult.getStringCursor();
				resultList =  scanResult.getResult();
			}while (!"0".equals(cursor));
		}catch (Exception e){
			if(jedis != null){
				redisConnectFactory.returnBrokenResource(jedis);
			}
		}finally {
			redisConnectFactory.returnResource(jedis);
		}
		return  resultList;
	}

	public RedisConnectFactory getRedisConnectFactory() {
		return redisConnectFactory;
	}

	public void setRedisConnectFactory(RedisConnectFactory redisConnectFactory) {
		this.redisConnectFactory = redisConnectFactory;
	}
}
