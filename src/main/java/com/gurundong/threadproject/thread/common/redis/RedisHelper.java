package com.gurundong.threadproject.thread.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Redis缓存封装类
 * 【注：目前只是封装了Redis的String/List两种数据结构的方法，直接使用redisTemplate也支持Hash/Set/ZSet
 * 后续如果有用到Hash/Set/ZSet可以再次进行封装到该方法中；String比较特殊，使用jar包自带的StringRedisTemplate】
 *
 * @author: xushiqiang
 * @date: 2018/11/3
 */
@Component
public class RedisHelper {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //返回一个redisTemplate;
    public RedisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }

    /**
     * 根据key删除缓存
     *
     * @param key 键
     */
    public Boolean removeForValue(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 获取指定key的失效时间
     *
     * @param key 键
     * @return 返回失效时间（单位：秒）
     */
    public Long getExpireForValue(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断redis中是否存在指定的key
     *
     * @param key 键
     * @return true:表示存在；false：不存在
     */
    public boolean isExistForValue(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 根据key删除缓存
     *
     * @param key 键
     */
    public Boolean removeForList(Object key) {
        return redisTemplate.delete(key);
    }

    /**
     * 获取指定key的失效时间
     *
     * @param key 键
     * @return 返回失效时间（单位：秒）
     */
    public Long getExpireForList(Object key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断redis中是否存在指定的key
     *
     * @param key 键
     * @return true:表示存在；false：不存在
     */
    public boolean isExistForList(Object key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 字符串型K-V  塞值
     *
     * @param key   键
     * @param value 值
     */
    public void setForValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 指定有效时间的存值
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间
     * @param unit    时间单位，传null 默认为秒
     */
    public void setForValue(String key, String value, long timeout, TimeUnit unit) {
        if (null == unit) {
            unit = SECONDS;
        }
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 如果缓存中存在该key，则存储失败，否则成功
     *
     * @param key   键
     * @param value 值
     * @return 塞值结果 true成功；false失败
     */
    public Boolean setIfAbsentForValue(String key, String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 为多个键同时设置值
     *
     * @param map 传入<KEY，VALUE>形式的map值，会批量存储
     */
    public void multiSetForValue(Map<String, String> map) {
        stringRedisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 获取多个键的值
     *
     * @param list 传入List<KEY>形式的集合，会批量获取
     * @return 返回List<String>结果集
     */
    public List<String> multiGetForValue(List<String> list) {
        return stringRedisTemplate.opsForValue().multiGet(list);
    }

    /**
     * 根据Key获取对应的值
     * [支持模糊查询，使用通配符 * ?  []]
     * *:表示不限制个数；?:限制字符的个数;[a,b] 表示只能查到中括号里面的字符
     *
     * @param key 键
     * @return 返回结果字符串
     */
    public String getForValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key获取到存储的字符串的长度
     *
     * @param key 键
     * @return 返回结果字符串的长度
     */
    public Long sizeForValue(String key) {
        return stringRedisTemplate.opsForValue().size(key);
    }

    /**
     * Redis的List数据结构
     * 【获取字符串列表】
     *
     * @param key   键
     * @param start 索引开始位置 0 是从列表的第一个位置开始
     * @param end   索引的结束位置  -1表示到最后一位
     * @return
     */
    public List<String> getForList(Object key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * Redis的List数据结构
     * 【修剪现有列表，使其只包含指定的指定范围的元素，起始和停止都是基于0的索引】
     *
     * @param key   键
     * @param start 索引开始位置 0 是从列表的第一个位置开始
     * @param end   索引的结束位置  -1表示到最后一位
     */
    public void trimForList(Object key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 返回存储在键中的列表的长度
     * [如果键不存在，则将其解释为空列表，并返回0。当key存储的值不是列表时返回错误]
     *
     * @param key 键
     * @return 返回列表的长度
     */
    public Long sizeForList(Object key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 将指定的值插入存储在键的列表的头部
     * 【如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）】
     *
     * @param key   键
     * @param value 值
     * @return 返回的结果为推送操作后的列表的长度
     */
    public Long leftPushForList(Object key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 批量把一个数组插入到列表中
     * 【如果键不存在，则在执行推送操作之前将其创建为空列表】
     *
     * @param key    键
     * @param values 数组值
     * @return 返回的结果为推送操作后的列表的长度
     */
    public Long leftPushAllForList(Object key, String[] values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 批量把一个List集合插入到列表中
     * 【如果键不存在，则在执行推送操作之前将其创建为空列表】
     *
     * @param key       键
     * @param valueList 集合列表值
     * @return 返回的结果为推送操作后的列表的长度
     */
    public Long leftPushAllForList(Object key, List<?> valueList) {
        return redisTemplate.opsForList().leftPushAll(key, valueList);
    }

    /**
     * 将指定的值插入存储在键的列表的尾部
     * 【如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）】
     *
     * @param key   键
     * @param value 值
     * @return 返回的结果为推送操作后的列表的长度
     */
    public Long rightPushForList(Object key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 批量把一个数组插入到列表尾部
     * 【如果键不存在，则在执行推送操作之前将其创建为空列表】
     *
     * @param key    键
     * @param values 数组值
     * @return 返回的结果为推送操作后的列表的长度
     */
    public Long rightPushAllForList(Object key, String[] values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 批量把一个List集合插入到列表尾部
     * 【如果键不存在，则在执行推送操作之前将其创建为空列表】
     *
     * @param key       键
     * @param valueList 集合列表值
     * @return 返回的结果为推送操作后的列表的长度
     */
    public Long rightPushAllForList(Object key, List<String> valueList) {
        return redisTemplate.opsForList().rightPushAll(key, valueList);
    }


    /**
     * 只有存在key对应的列表才能将这个value值插入到key所对应的列表中
     * [从左边表头插入]
     *
     * @param key   键
     * @param value 值
     * @return 返回0表示插入失败；插入成功则返回列表的长度
     */
    public Long leftPushIfPresentForList(Object key, String value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 将只有存在key对应的列表才能这个value值插入到key所对应的列表中
     * [从右边表头插入]
     *
     * @param key   键
     * @param value 值
     * @return 返回0表示插入失败；插入成功则返回列表的长度
     */
    public Long rightPushIfPresentForList(Object key, String value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 在列表中index的位置设置value值
     *
     * @param key
     * @param index
     * @param value
     */
    public void setForlist(Object key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 从存储在键中的列表中删除等于value值的第一个值，具体删除规则 根据count判断
     * count> 0：删除等于从头到尾移动的值第一个元素。
     * count <0：删除等于从尾到头移动的值第一个元素。
     * count = 0：删除等于value的所有元素。
     *
     * @param key   键
     * @param count 索引方向
     * @param value 值
     * @return
     */
    public Long removeForlist(Object key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 获取指定下标的list中存储的值
     *
     * @param key   键
     * @param index 下标
     * @return 返回String值
     */
    public String indexForlist(Object key, long index) {
        Object o = redisTemplate.opsForList().index(key, index);
        if (null != o) {
            return o.toString();
        }
        return null;
    }

    /**
     * 弹出最左边的元素，弹出之后该值在列表中将不复存在
     *
     * @param key 键
     * @return 返回表头第一条数据
     */
    public String leftPopForList(Object key) {
        Object o = redisTemplate.opsForList().leftPop(key);
        if (null != o) {
            return o.toString();
        }
        return null;
    }

    /**
     * 移出并获取列表的第一个元素
     * [如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止]
     *
     * @param key
     * @param timeout 超时时间
     * @param unit    时间单位 传值为null,则默认为秒
     * @return 返回
     */
    public String leftPopForList(Object key, long timeout, TimeUnit unit) {
        if (null == unit) {
            unit = SECONDS;
        }

        Object o = redisTemplate.opsForList().leftPop(key, timeout, unit);

        if (null != o) {
            return o.toString();
        }
        return null;
    }

    /**
     * 弹出最右边的元素，弹出之后该值在列表中将不复存在
     *
     * @param key 键
     * @return 返回最后一条数据
     */
    public String rightPopForList(Object key) {
        Object o = redisTemplate.opsForList().rightPop(key);
        if (null != o) {
            return o.toString();
        }
        return null;
    }

    /**
     * 移出并获取列表的第后一个元素
     * [如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止]
     *
     * @param key
     * @param timeout 超时时间
     * @param unit    时间单位 传值为null,则默认为秒
     * @return 返回
     */
    public String rightPopForList(Object key, long timeout, TimeUnit unit) {
        if (null == unit) {
            unit = SECONDS;
        }

        Object o = redisTemplate.opsForList().rightPop(key, timeout, unit);

        if (null != o) {
            return o.toString();
        }
        return null;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key     键
     * @param hashKey 项
     * @param value   值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hsetAll(String key, Map map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查看哈希表 hKey 中，给定域 hashKey 是否存在
     *
     * @param mapName 哈希表名称
     * @param field   域的hashKey
     * @return 如果哈希表含有给定域, 返回 true.如果哈希表不含有给定域,或 hashKey不存在,返回 false.
     */
    public Boolean hashCheckHxists(String mapName, String field) {
        return redisTemplate.opsForHash().hasKey(mapName, field);
    }

    /**
     * 查询哈希表 hKey 中给定域 hashKey 的值。
     *
     * @param tableName
     * @param hashKey
     * @return 给定域的值.当给定域不存在或是给定key不存在时, 返回 null
     */
    public Object hashGet(String tableName, String hashKey) {
        return redisTemplate.opsForHash().get(tableName, hashKey);
    }

    /**
     * 获取所有的散列值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hashGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 哈希表 hKey 中的域 hashKey 的值加上增量 delta 。
     * <p>
     * 增量也可以为负数，相当于对给定域进行减法操作.如果 key 不存在,一个新的哈希表被创建并执行HINCRBY 命令.
     * 如果域 field 不存在,那么在执行命令前,域的值被初始化为0.对一个储存字符串值的域 field 执行HINCRBY 命令将造成一个错误
     *
     * @param hKey
     * @param hashKey
     * @param delta
     * @return 执行 HINCRBY 命令之后，哈希表 hKey 中域 hashKey 的值。
     */
    public Long hashIncrementLongOfHashMap(String hKey, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(hKey, hashKey, delta);
    }

    /**
     * 哈希表 hKey 中的域 hashKey 的值加上浮点值 增量 delta
     *
     * @param hKey
     * @param hashKey
     * @param delta
     * @return 执行 HINCRBY 命令之后,哈希表hKey中域hashKey的值
     */
    public Double hashIncrementDoubleOfHashMap(String hKey, String hashKey, Double delta) {
        return redisTemplate.opsForHash().increment(hKey, hashKey, delta);
    }

    /**
     * 获取哈希表key中的所有域
     *
     * @param key
     * @return
     */
    public Set<Object> hashGetAllHashKey(String key) {
        return redisTemplate.opsForHash().keys(key);
    }


    /**
     * 获取散列中的字段数量
     *
     * @param key
     * @return
     */
    public Long hashGetHashMapSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希中的所有值
     *
     * @param key
     * @return
     */
    public List<Object> hashGetHashAllValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 删除一个或多个哈希字段
     *
     * @param key
     * @param hashKeys
     * @return 返回值为被成功删除的数量
     */
    public Long hashDeleteHashKey(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }


    /**
     * 批量把一个Set集合插入到列表
     * 【如果键不存在，则在执行添加操作之前将其创建为空列表】
     *
     * @param key      键
     * @param valueSet 集合列表值
     * @return 返回的结果为添加操作后的列表的长度
     * @author hushuhua
     * @date 2019/3/22 22:42
     */
    public Long addAllForSet(String key, Set<String> valueSet) {
        Object[] array = valueSet.toArray();
        return redisTemplate.opsForSet().add(key, array);
    }

    /**
     * @Description 写入缓存带锁(可以配置过期时间)
     * @Author shaoao 2018/10/31
     */
    public boolean setIfAbsent(final String key, Object value, Long expireTime)
    {
        boolean result = false;
        try
        {
            result = redisTemplate.opsForValue().setIfAbsent(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.HOURS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: 删除缓存
     * @Author: hj
     * @Date: 16:51 2017/10/24
     */
    public void remove(final String key)
    {
        if (exists(key))
        {
            redisTemplate.delete(key);
        }
    }

    /**
     * @Description: 判断缓存中是否有对应的value
     * @Author: hj
     * @Date: 16:50 2017/10/24
     */
    public boolean exists(final String key)
    {
        return redisTemplate.hasKey(key);
    }

}
