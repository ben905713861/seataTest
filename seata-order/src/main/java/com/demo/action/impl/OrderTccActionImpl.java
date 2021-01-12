/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.action.OrderTccAction;
import com.demo.entity.OrderDO;
import com.demo.entity.enumClass.TransactionLogStatus;
import com.demo.service.OrderService;
import com.demo.service.TransactionLogService;

import io.seata.rm.tcc.api.BusinessActionContext;

@Service
public class OrderTccActionImpl implements OrderTccAction {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TransactionLogService transactionLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean prepare(BusinessActionContext actionContext, OrderDO orderDO, int orderId) {
        Long branchId = actionContext.getBranchId();
        String xid = actionContext.getXid();

        System.err.println("TccActionOne prepare, xid:" + xid + ", orderId:" + orderId);

        // 幂等检查/防悬挂检查，已经存在log，直接返回成功
        if (transactionLogService.existsLog(branchId, xid)) {
            return true;
        }

        // 保存订单
        orderDO.setId(orderId);
        orderDO.setStatus(0);
        orderService.save(orderDO);

        // 记录事务log
        transactionLogService.insertInitLog(branchId, xid);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext actionContext) {
        Long branchId = actionContext.getBranchId();
        String xid = actionContext.getXid();
        int orderId = (int)actionContext.getActionContext("orderId");

        System.err.println("TccActionOne commit, xid:" + xid + ", orderId:" + orderId);

        // 幂等检查，已经存在log且status=comfirm/rollback，直接返回成功
        TransactionLogStatus logStatus = transactionLogService.getLogStatus(branchId, xid);
        if (logStatus != null && logStatus != TransactionLogStatus.INIT) {
            return true;
        }

        // 改变订单状态为正常状态
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setStatus(1);
        orderService.updateById(orderDO);

        // 能执行confirm，之前一定是执行了prepare
        transactionLogService.changeLogConfirmStatus(branchId, xid);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollback(BusinessActionContext actionContext) {
        Long branchId = actionContext.getBranchId();
        String xid = actionContext.getXid();
        int orderId = (int)actionContext.getActionContext("orderId");

        System.err.println("TccActionOne rollback, xid:" + xid + ", orderId:" + orderId);

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
        // 删除订单
        orderService.removeById(orderId);
        // 修改幂等记录
        transactionLogService.changeLogRollbackStatus(branchId, xid);
        return true;
    }

}
