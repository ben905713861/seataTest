/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.OrderDO;

public interface OrderService extends IService<OrderDO> {

    List<OrderDO> getOrderList(int userId);

}
