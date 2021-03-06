/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.UserDO;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    @Select("SELECT * FROM user WHERE id=#{userId} FOR UPDATE")
    UserDO getByIdForUpdate(int userId);

    @Update("update user set money=money-#{money} where id=#{userId}")
    int decreaseMoney(int userId, int money);

}
