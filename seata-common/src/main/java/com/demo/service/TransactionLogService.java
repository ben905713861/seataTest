/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.TransactionLogDO;
import com.demo.entity.enumClass.TransactionLogStatus;

public interface TransactionLogService extends IService<TransactionLogDO> {

    boolean existsLog(long branchId, String xid);

    TransactionLogStatus getLogStatus(long branchId, String xid);

    boolean insertInitLog(long branchId, String xid);

    boolean insertRollbackLog(long branchId, String xid);

    boolean changeLogConfirmStatus(long branchId, String xid);

    boolean changeLogRollbackStatus(long branchId, String xid);

}
