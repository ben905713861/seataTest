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

import io.seata.spring.annotation.GlobalTransactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseMoney(int userId, int money) {
        UserDO userDO = super.baseMapper.getByIdForUpdate(userId);
        int afterMoney = userDO.getMoney().intValue() - money;
        if (afterMoney < 0) {
            throw new RuntimeException("钱不够了");
        }
        userDO.setMoney(afterMoney);
        super.baseMapper.updateById(userDO);

        // try {
        // Thread.sleep(20000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // throw new RuntimeException("强制抛异常");
    }

    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public UserDO getById(int userId) {
        return super.baseMapper.getByIdForUpdate(userId);
    }

}
