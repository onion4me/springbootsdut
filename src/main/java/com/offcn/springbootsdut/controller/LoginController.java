package com.offcn.springbootsdut.controller;

import com.offcn.springbootsdut.model.BaseModel;
import com.offcn.springbootsdut.model.UserLogin;
import com.offcn.springbootsdut.utils.SdutRedisClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * 第一次请求 是将验证码生成，并传递给前端(如果是短信验证码，那么就不会给前端)
 * 第二步请求 是前端将验证码发给服务器，验证合法性
 *
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/23
 */
@RestController
@RequestMapping("/login")
@Api("用户登录相关的api")
public class LoginController {

    @Autowired
    private SdutRedisClient sdutRedisClient;

    @ApiOperation("获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", defaultValue = "0")
    })
    @RequestMapping(value = "/getcode", method = RequestMethod.GET)
    public BaseModel getCode(@RequestParam("phone") String phone) {
        Random random = new Random();
        int abs = Math.abs(random.nextInt(10000));
        UserLogin userLogin = new UserLogin();
        // 这个判断是演示，实际上手机号合法性校验比这个严格很多很多
        if (phone == null || phone.length() != 11) {
            userLogin.setCode(-1);
            userLogin.setMessage("手机号码错误");
            return userLogin;
        }
        // 第一件事 将二维码传递给客户端
        userLogin.setMessage("验证码获取成功，请查收");
        userLogin.setCheckCode(abs + "");
        // 第二件事 存储验证码到Redis
        sdutRedisClient.setStringValueToRedis(phone, abs, 30);
        return userLogin;
    }

    @ApiOperation("校验验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", defaultValue = "0"),
            @ApiImplicitParam(name = "code", value = "验证码", defaultValue = "0")
    })
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public BaseModel checkCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        BaseModel baseModel = new BaseModel();

        if (!sdutRedisClient.hasKey(phone)) {
            baseModel.setMessage("手机号码非法");
            return baseModel;
        }

        if (code.length()>=3){
            baseModel.setMessage("验证码格式不正确");
            return baseModel;
        }

        //1.根据手机号码从Redis中获取验证码
        Object stringValueFromRedis = sdutRedisClient.getStringValueFromRedis(phone);
        if (stringValueFromRedis == null) {
            baseModel.setCode(-1);
            baseModel.setMessage("验证码已过期，请重新生成");
            return baseModel;
        }

        if (stringValueFromRedis.equals(Integer.valueOf(code))) {
            baseModel.setMessage("验证码验证通过");
            sdutRedisClient.revemoKey(phone);
            return baseModel;
        } else {
            baseModel.setMessage("不匹配");
            return baseModel;
        }

    }
}
