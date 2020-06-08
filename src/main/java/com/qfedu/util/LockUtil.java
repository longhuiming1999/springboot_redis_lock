package com.qfedu.util;/*
 *@ClassName:LockUtil
 *@Author:lg
 *@Description:
 *@Date:2020/6/89:47
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class LockUtil {

    private String unLock = "local keyName = KEYS[1]\n" +
            "local keyValue = ARGV[1]\n" +
            "local result = redis.call('get',keyName)\n" +
            "if result == keyValue then\n" +
            "\tredis.call('del',keyName)\n" +
            "return '1'\n" +
            "end";

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ThreadLocal<String> uuid = new ThreadLocal<>();


    public boolean lock(String keyName, long time) {
        uuid.set(UUID.randomUUID().toString());

        Boolean result = redisTemplate.opsForValue().setIfAbsent(keyName, uuid.get(), time, TimeUnit.MILLISECONDS);

        return result;
    }

    public void unLock(String KeyName) {
        redisTemplate.execute(new DefaultRedisScript<>(unLock, String.class), Collections.singletonList(KeyName), uuid.get());
    }

}
