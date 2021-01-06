/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.OrderService;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String test() {
        var userId = 1;
        var money = 2;
        var goodName = "肥皂";
        orderService.createOrder(userId, goodName, money);
        return "hello";
    }

}
