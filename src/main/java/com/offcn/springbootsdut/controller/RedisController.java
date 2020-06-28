package com.offcn.springbootsdut.controller;

import com.offcn.springbootsdut.model.TbUser;
import com.offcn.springbootsdut.utils.RedisUtil;
import com.offcn.springbootsdut.utils.SdutRedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/22
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    //    @Autowired(required = true)
    @Autowired
//    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private SdutRedisClient sdutRedisClient;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public void sendDataToRedis() {

        // 过期时间未生效
        sdutRedisClient.setStringValueToRedis("test1","sdut",10);
        Map<Object, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("k","v");
        sdutRedisClient.setHashValueToRedis("hashKey",stringStringHashMap,10);

//        TbUser tbUser = new TbUser();
//        tbUser.setCode(100);
//        tbUser.setMessage("hello redis");
//        tbUser.setId(123);
//        tbUser.setName("zhangsan");
//        tbUser.setNickName("doudou");
//        redisUtil.set("tbUser", tbUser);
//
//        Map<String, Object> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.put("a", tbUser);
//        objectObjectHashMap.put("b", 1);
//        redisUtil.hmset("testMap", objectObjectHashMap, 30);

//        redisUtil.lSet("listKey","关羽");
//        redisUtil.lSet("listKey","张飞");
//        redisUtil.sSet("setKey","abc","def","sdut","offcn");
//        Set<Object> setKey = redisUtil.sGet("setKey");
//        System.out.println(setKey);
    }
}
