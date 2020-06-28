package com.offcn.springbootsdut.service;

import com.offcn.springbootsdut.model.TbUser;

import java.util.List;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/19
 */
public interface UserService {
    /**
     * 获取用户列表数据
     * @return 目标结果集
     */
    List<TbUser> findAllUserList();

    /**
     * 用户登录
     * @return
     */
    TbUser userLogin(String username,String password);
}
