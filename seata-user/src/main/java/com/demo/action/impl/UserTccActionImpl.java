/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.action.UserTccAction;
import com.demo.entity.enumClass.TransactionLogStatus;
import com.demo.service.TransactionLogService;
import com.demo.service.UserService;

import io.seata.rm.tcc.api.BusinessActionContext;

@Component
public class UserTccActionImpl implements UserTccAction {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionLogService transactionLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean prepare(BusinessActionContext actionContext, int userId, int money) {
        Long branchId = actionContext.getBranchId();
        String xid = actionContext.getXid();

        System.err.println("TccActionOne prepare, xid:" + xid + ", userId:" + userId + ", money:" + money);

        // 幂等检查/防悬挂检查，已经存在log，直接返回成功
        if (transactionLogService.existsLog(branchId, xid)) {
            return true;
        }

        // 冻结金额
        userService.frozenMoney(userId, money);

        // 记录事务log
        transactionLogService.insertInitLog(branchId, xid);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext actionContext) {
        Long branchId = actionContext.getBranchId();
        String xid = actionContext.getXid();
        int userId = (int)actionContext.getActionContext("userId");
        int money = (int)actionContext.getActionContext("money");

        System.err.println("TccActionOne commit, xid:" + xid + ", userId:" + userId + ", money:" + money);

        // 幂等检查，已经存在log且status=comfirm/rollback，直接返回成功
        TransactionLogStatus logStatus = transactionLogService.getLogStatus(branchId, xid);
        if (logStatus != null && logStatus != TransactionLogStatus.INIT) {
            return true;
        }

        // 真实扣钱，扣掉冻结金额
        userService.decreaseFrozenMoney(userId, money);

        // 能执行confirm，之前一定是执行了prepare
        transactionLogService.changeLogConfirmStatus(branchId, xid);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollback(BusinessActionContext actionContext) {
        Long branchId = actionContext.getBranchId();
        String xid = actionContext.getXid();
        int userId = (int)actionContext.getActionContext("userId");
        int money = (int)actionContext.getActionContext("money");

        System.err.println("TccActionOne rollback, xid:" + xid + ", userId:" + userId + ", money:" + money);

        // 幂等检查，已经存在log且status=comfirm/rollback，直接返回成功
        TransactionLogStatus logStatus = transactionLogService.getLogStatus(branchId, xid);
        if (logStatus != null && logStatus != TransactionLogStatus.INIT) {
            return true;
        }

        // 空回滚检查，status==null说明执行空回滚
        if (logStatus == null) {
            // 插入防悬挂记录
            transactionLogService.insertRollbackLog(branchId, xid);
            return true;
        }
        // 回滚扣钱
        userService.frozenMoney(userId, -money);
        // 修改幂等记录
        transactionLogService.changeLogRollbackStatus(branchId, xid);
        return true;
    }

}
