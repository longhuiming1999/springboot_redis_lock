package com.qfedu.service.impl;/*
 *@ClassName:ClickServiceImpl
 *@Author:lg
 *@Description:
 *@Date:2020/6/721:01
 */

import com.qfedu.entity.ClickNumber;
import com.qfedu.mapper.ClickMapper;
import com.qfedu.service.IClickService;
import com.qfedu.util.LockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;

@Service
public class ClickServiceImpl implements IClickService {

    @Resource
    private ClickMapper clickMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String lockScript = "local result = tonumber(redis.call('setnx',KEYS[1],ARGV[1])) " +
            "if result == 1 then redis.call('expire',KEYS[1],ARGV[2]) return '1' else return '0' end";


    private String unLock = "local keyName = KEYS[1]\n" +
            "local keyValue = ARGV[1]\n" +
            "local result = redis.call('get',keyName)\n" +
            "if result == keyValue then\n" +
            "\tredis.call('del',keyName)\n" +
            "return '1'\n" +
            "end";

    @Autowired
    private LockUtil lockUtil;

    @Override
    public boolean clickNumber() {

        if (lockUtil.lock("lock", 10000L)) {
            ClickNumber clickNumber = clickMapper.selectById(1);
            clickNumber.setNumber(clickNumber.getNumber() + 1);
            clickMapper.updateById(clickNumber);

            lockUtil.unLock("lock");

        } else {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clickNumber();
        }


        return false;
    }
}
