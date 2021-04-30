package com.zyblue.fastim.common.mytest.designpatters.statebuilder.order;

/**
 * @author will
 * @date 2021/4/27 17:47
 */
public interface FsmOrder {
    /**
     * 订单ID
     */
    String getOrderId();
    /**
     * 订单状态
     */
    String getOrderState();
    /**
     * 订单的业务属性
     */
    String bizCode();
    /**
     * 订单的场景属性
     */
    String sceneId();
}
