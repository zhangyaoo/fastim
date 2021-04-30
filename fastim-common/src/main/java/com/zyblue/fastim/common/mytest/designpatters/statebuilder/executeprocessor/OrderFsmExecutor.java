package com.zyblue.fastim.common.mytest.designpatters.statebuilder.executeprocessor;

import com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder.ServiceResult;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.order.FsmOrder;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.orderstateevent.OrderStateEvent;

/**
 * @author will
 * @date 2021/4/28 9:35
 */
public interface OrderFsmExecutor {
    /**
     * 执行状态迁移事件，不传FsmOrder默认会根据orderId从FsmOrderService接口获取
     */
    <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent) throws Exception;
    /**
     * 执行状态迁移事件，可携带FsmOrder参数
     */
    <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) throws Exception;
}
