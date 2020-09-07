package com.gurundong.threadproject.thread.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redicache 工具类
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtil
{
    @SuppressWarnings("rawtypes")
    @Autowired
    @Qualifier("str-json-template")
    private RedisTemplate redisTemplate;

    /**
     * @Description: 批量删除缓存
     * @Author: hj
     * @Date: 17:13 2017/10/24
     */
    public void remove(final String... keys)
    {
        for (String key : keys)
        {
            remove(key);
        }
    }

    /**
     * @Description: 批量删除缓存(通配符)
     * @Author: hj
     * @Date: 16:52 2017/10/24
     */
    public void removePattern(final String pattern)
    {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
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

    /**
     * @Description: 读取缓存
     * @Author: hj
     * @Date: 16:49 2017/10/24
     */
    public Object get(final String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    public Long getSize(final String key){
        return redisTemplate.opsForList().size(key);
    }

    /**
     * @Description 写入缓存
     * @Author hj
     * @Date shaoao 2018/10/31
     */
    public boolean set(final String key, Object value)
    {
        boolean result = false;
        try
        {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description 写入缓存(可以配置过期时间)
     * @Author shaoao 2018/10/31
     */
    public boolean set(final String key, Object value, Long expireTime)
    {
        boolean result = false;
        try
        {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束 0 到 -1代表所有值
     */
    public List<Object> lGet(String key, long start, long end)
    {
        try
        {
            return redisTemplate.opsForList().range(key, start, end);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     */
    public long lGetListSize(String key)
    {
        try
        {
            return redisTemplate.opsForList().size(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1
     *        第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public Object lGetIndex(String key, long index)
    {
        try
        {
            return redisTemplate.opsForList().index(key, index);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 放入Redis队列中
     * @param key 键
     * @param value 值
     */
    public boolean lpush(String key, Object value)
    {
        try
        {
            redisTemplate.opsForList().leftPush(key, value);
            System.out.println(redisTemplate.getConnectionFactory());
            redisTemplate.getConnectionFactory();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 放入Redis队列中,带有效期
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     */
    public boolean lpush(String key, Object value, long time)
    {
        try
        {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入Redis队列
     * @param key 键
     * @param value 值
     */
    public boolean lpush(String key, List<Object> value)
    {
        try
        {
            redisTemplate.opsForList().leftPushAll(key, value);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入Redis队列,带有效期
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     */
    public boolean lpush(String key, List<Object> value, long time)
    {
        try
        {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 放入Redis队列中
     * @param key 键
     * @param value 值
     */
    public boolean rpush(String key, Object value)
    {
        try
        {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 放入Redis队列中,带有效期
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     */
    public boolean rpush(String key, Object value, long time)
    {
        try
        {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入Redis队列中
     * @param key 键
     * @param value 值
     */
    public boolean rpush(String key, List<Object> value)
    {
        try
        {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入Redis队列中,带有效期
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     */
    public boolean rpush(String key, List<Object> value, long time)
    {
        try
        {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取元素并移除
     * @param key 键
     */
    public Object lpop(String key)
    {
        try
        {
            return redisTemplate.opsForList().leftPop(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取元素并移除
     * @param key 键
     */
    public Object rpop(String key)
    {
        try
        {
            return redisTemplate.opsForList().rightPop(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 移除元素
     * @param key
     * @param time
     * @param unit
     * @return
     */
    public boolean rpop(String key, long time, TimeUnit unit)
    {
        try
        {
            redisTemplate.opsForList().rightPop(key, time, unit);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * set集合存储
     * @param key 键
     * @param value 值
     */
    public boolean sadd(String key, Object value)
    {
        try
        {
            redisTemplate.opsForSet().add(key, value);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     */
    public boolean lUpdateIndex(String key, long index, Object value)
    {
        try
        {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value)
    {
        try
        {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time)
    {
        try
        {
            if (time > 0)
            {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key 键
     * @param hashKey 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String hashKey, Object value)
    {
        try
        {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * redis的链接在高并发下及时释放链接
     */
    public void releaseConnection(){
        RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }

    /**
     * 根据需求切换数据库
     * @param redisTemplate 基于springboot的Redis模板对象
     */
    public static void setDB4RedisTemplate(RedisTemplate<String, String> redisTemplate, int dbIndex) {
        LettuceConnectionFactory lcf = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        if (lcf != null) {
            lcf.setDatabase(dbIndex);
            redisTemplate.setConnectionFactory(lcf);
        }
    }

}