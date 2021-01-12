/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.action.UserTccAction;

@RestController
@RequestMapping("/userTccApi")
public class UserTccApi {

    @Autowired
    private UserTccAction userTccAction;

    @GetMapping("/prepare")
    public boolean prepare(Integer userId, Integer money) {
        return userTccAction.prepare(null, userId, money);
    }

}
