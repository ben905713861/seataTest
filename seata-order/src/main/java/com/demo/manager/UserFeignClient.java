/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月5日
 * @description
 */

package com.demo.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://127.0.0.1:8081")
public interface UserFeignClient {

    @GetMapping("/test/decreaseMoney")
    String decreaseMoney(@RequestParam("userId") int userId, @RequestParam("money") int money);

}
