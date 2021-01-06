/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.entity.UserDO;
import com.demo.mapper.UserMapper;
import com.demo.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseMoney(int userId, int money) {

        /**
         * SELECT * FROM user WHERE id=#{userId} FOR UPDATE
         * 不执行这句话不会获取到全局锁，第二个全局事务的update会在第一个全局事务没完成的时候就执行
         */
        // super.baseMapper.getByIdForUpdate(userId);

        /**
         * update user set money=money-#{money} where id=#{userId}
         */
        super.baseMapper.decreaseMoney(userId, money);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("强制抛异常");
    }

}
