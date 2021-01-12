/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.entity.TransactionLogDO;
import com.demo.entity.enumClass.TransactionLogStatus;
import com.demo.mapper.TransactionLogMapper;
import com.demo.service.TransactionLogService;

@Service
public class TransactionLogServiceImpl extends ServiceImpl<TransactionLogMapper, TransactionLogDO>
    implements TransactionLogService {

    @Override
    public boolean existsLog(long branchId, String xid) {
        TransactionLogDO transactionLogDO = getLog(branchId, xid);
        return transactionLogDO != null;
    }

    @Override
    public TransactionLogStatus getLogStatus(long branchId, String xid) {
        TransactionLogDO transactionLogDO = getLog(branchId, xid);
        if (transactionLogDO == null) {
            return null;
        }
        return transactionLogDO.getStatus();
    }

    @Override
    public boolean insertInitLog(long branchId, String xid) {
        return insertLog(branchId, xid, TransactionLogStatus.INIT);
    }

    @Override
    public boolean insertRollbackLog(long branchId, String xid) {
        return insertLog(branchId, xid, TransactionLogStatus.ROLLBACKED);
    }

    @Override
    public boolean changeLogConfirmStatus(long branchId, String xid) {
        return updateLog(branchId, xid, TransactionLogStatus.CONFIRMED);
    }

    @Override
    public boolean changeLogRollbackStatus(long branchId, String xid) {
        return updateLog(branchId, xid, TransactionLogStatus.ROLLBACKED);
    }

    private TransactionLogDO getLog(long branchId, String xid) {
        QueryWrapper<TransactionLogDO> queryWrapper = new QueryWrapper<TransactionLogDO>();
        queryWrapper.eq("branch_id", branchId);
        queryWrapper.eq("xid", xid);
        queryWrapper.last("FOR UPDATE");
        return super.getOne(queryWrapper);
    }

    private boolean insertLog(long branchId, String xid, TransactionLogStatus status) {
        TransactionLogDO entity = new TransactionLogDO();
        entity.setBranchId(branchId);
        entity.setXid(xid);
        entity.setStatus(status);
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        return super.save(entity);
    }

    private boolean updateLog(long branchId, String xid, TransactionLogStatus status) {
        UpdateWrapper<TransactionLogDO> updateWrapper = new UpdateWrapper<TransactionLogDO>();
        updateWrapper.eq("branch_id", branchId);
        updateWrapper.eq("xid", xid);
        updateWrapper.set("status", status.ordinal());
        updateWrapper.set("update_time", new Date());
        return super.update(updateWrapper);
    }

}
