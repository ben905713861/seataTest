/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.entity.enumClass;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum TransactionLogStatus implements IEnum<Integer> {

    INIT(0, "初始化"),
    CONFIRMED(1, "已提交"),
    ROLLBACKED(2, "已回滚");

    private final Integer value;
    private final String name;

    TransactionLogStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
