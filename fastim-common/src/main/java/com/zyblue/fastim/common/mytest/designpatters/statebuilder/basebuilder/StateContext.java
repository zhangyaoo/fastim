package com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder;

import com.zyblue.fastim.common.mytest.designpatters.statebuilder.order.FsmOrder;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.orderstateevent.OrderStateEvent;

/**
 * @author will
 * @date 2021/4/27 10:18
 *
 * 使用一个上下文Context对象做为每个方法的入参传递
 * 在多个方法中进行传递
 */
public class StateContext<C> {
    /**
     * 订单操作事件
     */
    private OrderStateEvent orderStateEvent;
    /**
     * 状态机需要的订单基本信息
     */
    private FsmOrder fsmOrder;
    /**
     * 业务可定义的上下文泛型对象
     */
    private C context;

    public StateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) {
        this.orderStateEvent = orderStateEvent;
        this.fsmOrder = fsmOrder;
    }

    public OrderStateEvent getOrderStateEvent() {
        return orderStateEvent;
    }

    public void setOrderStateEvent(OrderStateEvent orderStateEvent) {
        this.orderStateEvent = orderStateEvent;
    }

    public FsmOrder getFsmOrder() {
        return fsmOrder;
    }

    public void setFsmOrder(FsmOrder fsmOrder) {
        this.fsmOrder = fsmOrder;
    }

    public C getContext() {
        return context;
    }

    public void setContext(C context) {
        this.context = context;
    }
}
