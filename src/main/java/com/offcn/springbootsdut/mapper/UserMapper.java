package com.offcn.springbootsdut.mapper;

import com.offcn.springbootsdut.model.TbUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**ibatis 是 mybatis早期的名
 * @link{com.offcn.springbootsdut.mapper} 等同于我们之前的dao包
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/19
 */
@Mapper
public interface UserMapper {

    @Select("select * from tb_user")
    List<TbUser> findAllUserList();

    @Select("select * from tb_user where username=#{username} and password=#{password}")
    TbUser userLogin(@Param("username") String username, @Param("password")String password);
}
