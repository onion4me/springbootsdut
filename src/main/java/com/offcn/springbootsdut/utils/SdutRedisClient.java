package com.offcn.springbootsdut.utils;

import com.offcn.springbootsdut.exception.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/22
 */

@Component
public class SdutRedisClient {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HashOperations<String, Object, Object> hashOperations;

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Autowired
    private ListOperations<String, Object> listOperations;

    @Autowired
    private SetOperations<String, Object>  setOperations;

    @Autowired
    private ZSetOperations<String, Object>  zSetOperations;

    /**
     * 按照简单的key-value形式将数据存储到Redis
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setStringValueToRedis(String key, Object value) {
        try {
            //ValueOperations<K, V>
//            ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
//            stringObjectValueOperations.set(key, value);
//            redisTemplate.opsForValue().set(key,value);
            valueOperations.set(key,value);
            return true;
        } catch (RedisException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 按照简单的key-value形式将数据存储到Redis
     *
     * @param key
     * @param value
     * @param time  缓存数据失效的时间
     * @return
     */
    public boolean setStringValueToRedis(String key, Object value, long time) {
        try {
            valueOperations.set(key, value, time,TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 以Hash结构组织数据并存入Reids
     *
     * @param key
     * @param map
     * @return
     */
    public boolean setHashValueToRedis(String key, Map<Object, Object> map) {
        try {
            //redisTemplate.opsForHash();
            hashOperations.putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setHashValueToRedis(String key, Map<Object, Object> map, long time) {
        // 第一种方式  如果下面代码报错，那么我在当前位置直接处理
        // 第二种方式  如果下面代码报错，那么我会把错误扔给调用方，谁调用谁处理
        try {
            hashOperations.putAll(key, map);
            setExpire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 插入数据，如果不存在则创建一个新的keyvalue
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean setHashValueToRedis(String key, String item, Object value) {
        try {
            hashOperations.put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setHashValueToRedis(String key, String item, Object value, long time) {
        try {
            hashOperations.put(key, item, value);
            setExpire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取数据
     *
     * @param key
     * @param item
     * @return
     */
    public Object getHashValueFromRedis(String key, Object item) {
        return hashOperations.get(key, item);
    }

    /**
     * 根据key直接将整个map抓取出来
     *
     * @param key
     * @return
     */
    public Map<Object, Object> getHashValueFromRedis(String key) {
        return hashOperations.entries(key);
    }

    /**
     * 删除hash表中的值
     *
     * @param key
     * @param item
     */
    public void removeHashValueFromRedis(String key, Object... item) {
        hashOperations.delete(key, item);
    }

    /**
     * 删除整个hash结构
     *
     * @param key
     */
    public void removeHashValueFromRedis(String key) {
        revemoKey(key);
    }

    /**
     * 判断该key对应的哈希表中是否包含该项的值
     *
     * @param key
     * @param item
     * @return
     */
    public boolean containsHashValueFromRedis(String key, String item) {
        return hashOperations.hasKey(key, item);
    }

    /**
     * 将内容按照set结构存入redis
     *
     * @param key
     * @param values
     * @return
     */
    public long setSetValueToRedis(String key, Object... values) {
        try {
            return setOperations.add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long setSetValueToRedis(String key, long time, Object... values) {
        try {
            Long add = setOperations.add(key, values);
            setExpire(key, time);
            return add;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key
     * @return
     */
    public Set<Object> getSetValueFromRedis(String key) {
        try {
            return setOperations.members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key
     * @param value
     * @return
     */
    public boolean containsSetValueFromRedis(String key, Object value) {
        try {
            return setOperations.isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 取set缓存的长度
     *
     * @param key
     * @return
     */
    public long getSetValueLengthFromRedis(String key) {
        try {
            return setOperations.size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的元素
     *
     * @param key
     * @param values
     * @return
     */
    public long removeSetValueFromRedis(String key, Object... values) {
        try {
            Long count = setOperations.remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long removeSetValueFromRedis(String key) {
        try {
            revemoKey(key);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 按照list结构,以右压栈的形式将数据放入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setListValueRightToRedis(String key, Object value) {
        try {
           listOperations.rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setListValueRightToRedis(String key, Object value, long time) {
        try {
            listOperations.rightPush(key, value);
            setExpire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将List以右亚栈的形式批量放入缓存
     *
     * @return
     */
    public boolean setListRightToRedis(String key, List<Object> value) {
        try {
            listOperations.rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setListRightToRedis(String key, List<Object> value, long time) {
        try {
            listOperations.rightPushAll(key, value);
            setExpire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将List以左亚栈的形式批量放入缓存
     *
     * @return
     */
    public boolean setListLeftToRedis(String key, List<Object> value) {
        try {
            listOperations.rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setListLeftToRedis(String key, List<Object> value, long time) {
        try {
            listOperations.leftPushAll(key, value);
            setExpire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 按照list结构,以左压栈的形式将数据放入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setListValueLeftToRedis(String key, Object value) {
        try {
            listOperations.leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setListValueLeftToRedis(String key, Object value, long time) {
        try {
            listOperations.leftPush(key, value);
            setExpire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从List中截取数据片段
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> getListValueRangeFromRedis(String key, long start, long end) {
        try {
            return listOperations.range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key
     * @param index
     * @return
     */
    public Object getListValueIndexFromRedis(String key, long index) {
        try {
            return listOperations.index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key
     * @return
     */
    public long getListValueLengthFromRedis(String key) {
        try {
            return listOperations.size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据 key 和list索引更新指定数据
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean updateListValueToRedis(String key, long index, Object value) {
        try {
            listOperations.set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long removeListFromRedis(String key) {
        try {
            revemoKey(key);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据值移除数据
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long removeListValueFromRedis(String key, long count, Object value) {
        try {
            Long remove = listOperations.remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 弹出List右侧元素
     *
     * @return
     */
    public Object removeListValueRightFromRedis(String listKey) {
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        return boundValueOperations.rightPop();
    }

    /**
     * 弹出List左侧元素
     *
     * @return
     */
    public Object removeListValueLeftFromRedis(String listKey) {
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        return boundValueOperations.leftPop();
    }

    /**
     * 根据key获取value信息
     *
     * @param key
     * @return
     */
    public Object getStringValueFromRedis(String key) {
        if (key != null && hasKey(key)) {
            return redisTemplate.opsForValue().get(key);
        }
        return null;
        //语法糖
//        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void revemoKey(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key
     * @param time
     * @return
     */
    public boolean setExpire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

}
