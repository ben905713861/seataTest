/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        localAddOrder(userId, goodName, money);
        String res1 = userFeignClient.decreaseMoney(userId, money);
        System.err.println(res1);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("强制抛异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void localAddOrder(int userId, String goodName, int money) {
        OrderDO orderDO = new OrderDO();
        orderDO.setUserId(userId);
        orderDO.setGoodName(goodName);
        orderDO.setPrice(money);
        super.save(orderDO);
    }

    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public List<OrderDO> getOrderList(int userId) {
        return super.baseMapper.listByUserId(userId);
    }

}
