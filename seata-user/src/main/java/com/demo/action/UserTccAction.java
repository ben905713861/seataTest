/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月12日
 * @description
 */

package com.demo.action;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface UserTccAction {

    @TwoPhaseBusinessAction(name = "UserTccAction")
    boolean prepare(BusinessActionContext actionContext,
        @BusinessActionContextParameter(paramName = "userId") int userId,
        @BusinessActionContextParameter(paramName = "money") int money);

    /**
     * Commit boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean rollback(BusinessActionContext actionContext);

}
