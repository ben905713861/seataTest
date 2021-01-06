/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.entity.OrderDO;
import com.demo.manager.UserFeignClient;
import com.demo.mapper.OrderMapper;
import com.demo.service.OrderService;

import io.seata.spring.annotation.GlobalTransactional;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    @GlobalTransactional
    public void createOrder(int userId, String goodName, int money) {
        var orderDO = new OrderDO();
        orderDO.setUserId(userId);
        orderDO.setGoodName(goodName);
        orderDO.setPrice(money);
        super.save(orderDO);

        var res1 = userFeignClient.decreaseMoney(userId, money);
        System.out.println(res1);
    }

}
