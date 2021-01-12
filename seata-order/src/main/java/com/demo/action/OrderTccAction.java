/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.action;

import com.demo.entity.OrderDO;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface OrderTccAction {

    @TwoPhaseBusinessAction(name = "OrderTccAction")
    boolean prepare(BusinessActionContext actionContext, OrderDO orderDO,
        @BusinessActionContextParameter(paramName = "orderId") int orderId);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);

}
