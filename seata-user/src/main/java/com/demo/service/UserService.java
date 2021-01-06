/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.UserDO;

public interface UserService extends IService<UserDO> {

    void decreaseMoney(int userId, int money);

}
