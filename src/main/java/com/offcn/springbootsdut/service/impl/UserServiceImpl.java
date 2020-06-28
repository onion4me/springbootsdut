package com.offcn.springbootsdut.service.impl;

import com.offcn.springbootsdut.mapper.UserMapper;
import com.offcn.springbootsdut.model.TbUser;
import com.offcn.springbootsdut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<TbUser> findAllUserList() {
//        ArrayList<TbUser> tbUsers = new ArrayList<>();
//        TbUser tbUser = new TbUser();
//        tbUser.setEmail("123@qq.com");
//        tbUsers.add(tbUser);
        return userMapper.findAllUserList();
    }

    @Override
    public TbUser userLogin(String username, String password) {
        return userMapper.userLogin(username,password);
    }

}
