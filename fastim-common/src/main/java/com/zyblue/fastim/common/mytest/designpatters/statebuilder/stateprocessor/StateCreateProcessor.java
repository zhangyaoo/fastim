package com.zyblue.fastim.common.mytest.designpatters.statebuilder.stateprocessor;

import com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder.AbstractStateProcessor;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.annotion.OrderProcessor;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder.ServiceResult;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder.StateContext;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.check.Checkable;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.check.Checker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author will
 * @date 2021/4/27 10:37
 */
@OrderProcessor(state = "init", bizCode = "code1", sceneId = "scene1", event = "event1")
public class StateCreateProcessor extends AbstractStateProcessor<String, CreateOrderContext> {

    private Checker createParamChecker;

    private Checker userChecker;

    private Checker unfinshChecker;

    @Override
    public ServiceResult<String> check(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public String getNextState(StateContext<CreateOrderContext> context) {
        //return OrderStateEnum.NEW;
        return null;
    }

    @Override
    public ServiceResult<String> action(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        // 促销信息信息
        /**
         * 背景：正编码的时候会发现不同类型不同维度对于同一个状态的流程处理过程，有时多个处理逻辑中的一部分流程一样的或者是相似的
         */
        String promtionInfo = this.doPromotion();
        return null;
    }

    /**
     * 促销相关扩展点
     * 背景：对于一些相同的状态的的不同场景逻辑，大部分相同，但是少数逻辑不同的，可以做抽象处理
     * 实施：所有的标准处理流程和可扩展点进行封装实现、其他处理器进行继承、覆写、替换就好。
     */
    protected String doPromotion() {
        return "促销信息逻辑";
    }

    @Override
    public ServiceResult<String> save(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        //OrderInfo orderInfo = context.getContext().getOrderInfo();
        // 更新状态
        //orderInfo.setOrderState(nextState);
        // 持久化
        // this.updateOrderInfo(orderInfo);
        //log.info("save BUSINESS order success, userId:{}, orderId:{}", orderInfo.getUserId(), orderInfo.getOrderId());
        return new ServiceResult<>("orderInfo.getOrderId()", "business下单成功");
    }

    @Override
    public void after(StateContext<CreateOrderContext> context) {
        // TODO 发送MQ
    }

    /**
     * 获取校验器
     * 分为参数校验（paramChecker）、同步校验（syncChecker）、异步校验（asyncChecker）三种类型，
     * 其中参数校验paramChecker是需要在状态处理器最开始处执行的
     */
    @Override
    public Checkable getCheckable(StateContext<CreateOrderContext> context) {
        return new Checkable() {
            @Override
            public List<Checker> getParamChecker() {
                return Arrays.asList(createParamChecker);
            }
            @Override
            public List<Checker> getSyncChecker() {
                return Collections.EMPTY_LIST;
            }
            @Override
            public List<Checker> getAsyncChecker() {
                return Arrays.asList(userChecker, unfinshChecker);
            }
        };
    }

    /**
     * 给业务开一个口、由业务决定从多个处理器中选一个适合当前上下文的
     * @param context
     * @return
     */
    @Override
    public boolean filter(StateContext<CreateOrderContext> context) {
        /*OrderInfo orderInfo = (OrderInfo) context.getFsmOrder();
        if (orderInfo.getServiceType() == ServiceType.TAKEOFF_CAR) {
            return true;
        }*/
        return false;
    }
}
