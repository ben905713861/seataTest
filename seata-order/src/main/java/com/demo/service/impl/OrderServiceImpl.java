/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.entity.OrderDO;
import com.demo.mapper.OrderMapper;
import com.demo.service.OrderService;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    @Override
    public List<OrderDO> getOrderList(int userId) {
        return super.baseMapper.listByUserId(userId);
    }

}
