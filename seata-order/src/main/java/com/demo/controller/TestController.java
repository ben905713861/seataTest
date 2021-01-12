/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.OrderDO;
import com.demo.service.BusinessService;
import com.demo.service.OrderService;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BusinessService businessService;

    @GetMapping
    public String test() {
        int userId = 1;
        int money = 2;
        String goodName = "肥皂";
        businessService.doAction(userId, goodName, money);
        return "hello";
    }

    @GetMapping("orderList")
    public List<OrderDO> getOrderList() {
        int userId = 1;
        return orderService.getOrderList(userId);
    }

}
