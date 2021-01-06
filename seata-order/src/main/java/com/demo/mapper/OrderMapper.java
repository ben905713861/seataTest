/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.OrderDO;

@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

    @Select("SELECT * FROM `order` WHERE id=#{orderId} FOR UPDATE")
    OrderDO getByIdForUpdate(int orderId);

}
