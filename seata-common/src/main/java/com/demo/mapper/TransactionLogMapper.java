/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.TransactionLogDO;

@Mapper
public interface TransactionLogMapper extends BaseMapper<TransactionLogDO> {

}
