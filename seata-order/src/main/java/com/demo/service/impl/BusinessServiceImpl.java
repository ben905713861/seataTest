/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.action.OrderTccAction;
import com.demo.entity.OrderDO;
import com.demo.manager.UserTccFeignClient;
import com.demo.service.BusinessService;

import io.seata.spring.annotation.GlobalTransactional;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private OrderTccAction orderTccAction;
    @Autowired
    private UserTccFeignClient userTccFeignClient;

    @Override
    @GlobalTransactional
    public void doAction(int userId, String goodName, int money) {
        OrderDO orderDO = new OrderDO();
        orderDO.setUserId(userId);
        orderDO.setGoodName(goodName);
        orderDO.setPrice(money);
        int orderId = new Random().nextInt(100);
        boolean res0 = orderTccAction.prepare(null, orderDO, orderId);
        System.err.println(res0);

        boolean res1 = userTccFeignClient.prepare(userId, money);
        System.err.println(res1);

        // try {
        // Thread.sleep(10000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // throw new RuntimeException("强制抛异常");
    }

}
