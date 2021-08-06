package com.jiangwei.springboottest.myboot.config.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @author: weijiang
 * @date: 2021/5/7
 * @description: redis 和 fusion的方法包装类,
 * NOTE: 整个方法使用JsonJackSon序列化器，这里因时间紧张没有多实现其他序列化器，后期可扩展
 **/
@Slf4j
@Component
public class RedisProtocolBox {

    private static final String DEFAULT_CACHE = "so-center";
    private static final String DEFAULT_KEY_PREFIX = "proxy";

    @Resource(name = "jedisClient")
    private Jedis jedisClient;

    @Resource(name = "jedisClient")
    private Jedis fusionClient;

    @Resource
    JacksonCodec jacksonCodec;


    /**
     * 从redis中获取对象
     * NOTE: 本方法只是实现了泛型的泛型对象转化，如果泛型超过2层嵌套，本方法不适用
     * @param name
     * @param typeReference
     * @param <V>
     * @return
     */
    public <V> V getObjFromRedis(String name, TypeReference<V> typeReference) {
        try {
            String key = buildKey(name);
            String val = jedisClient.get(key);
            return jacksonCodec.decodeVal(val, typeReference);
        } catch (Exception e) {
            log.error("_redisProtocolBox_Error||method=getObjFromRedis||name={}||vClass={}||errorMsg={}.",
                    name, typeReference.toString(), e.getMessage());
        }
        return null;

    }

    /**
     * 从fusion中获取对象
     * NOTE: 本方法只是实现了泛型的泛型对象转化，如果泛型超过2层嵌套，本方法不适用
     * @param name
     * @param typeReference
     * @param <V>
     * @return
     */
    public <V> V getObjFromFusion(String name, TypeReference<V> typeReference) {
        try {
            String key = buildKey(name);
            String val = fusionClient.get(key);
            return jacksonCodec.decodeVal(val, typeReference);
        } catch (Exception e) {
            log.error("_redisProtocolBox_Error||method=getObjFromFusion||name={}||vClass={}||errorMsg={}.",
                    name, typeReference.toString(), e.getMessage());
        }
        return null;
    }


    /**
     * 保存对象到redis内存并附着过期时间（秒）
     * true: 保存成功  false:保存失败
     *
     * @param key
     * @param value
     * @param expiredTime
     */

    /*public boolean setObj2RedisWithExpSecond(String key, Object value, int expiredTime) {
        try {
            String cacheKey = buildKey(key);
            String val = jacksonCodec.encodeVal(value);
            SetParams setParams = SetParams.setParams();
            String rest = jedisClient.set(cacheKey, val, setParams.ex(expiredTime));
            if (StringUtils.isNotEmpty(rest) && "OK".equals(rest)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("_redisProtocolBox_Error||method=setObj2RedisWithExpSecond||key={}||value={}||expiredTime={}",
                    key, JSON.toJSONString(value), expiredTime, e);
            return false;
        }
    }*/


    /**
     * 构建含有业务前缀的key
     *
     * @param name
     * @return
     */
    private String buildKey(String name) {
        return String.format("%s_%s", DEFAULT_KEY_PREFIX, name);
    }



}
