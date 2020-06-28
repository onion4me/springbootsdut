package com.offcn.springbootsdut.controller;

import com.offcn.springbootsdut.SdutArrayList;
import com.offcn.springbootsdut.model.TbUser;
import com.offcn.springbootsdut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/19
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findAllUserList",method = RequestMethod.GET)
    public List<TbUser> findAllUserList(){
        return userService.findAllUserList();
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public TbUser userLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        TbUser tbUser = userService.userLogin(username, password);
        //目前这个逻辑是存在优化空间的！！
        //所以 要透过表象看本质，即便你认为是没问题的！
        //http://localhost/user/login?username=sunwukong&password123
        //http://localhost/user/login?username=sunwukong&password=123
        if (null == tbUser){
            tbUser = new TbUser();
            tbUser.setCode(10010);
            tbUser.setMessage("没有这个用户");
        }
        return tbUser;
    }

}
