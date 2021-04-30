package com.zyblue.fastim.common.mytest.designpatters.statebuilder.executeprocessor;

import com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder.AbstractStateProcessor;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder.ServiceResult;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder.StateContext;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.initprocessor.DefaultStateProcessRegistry;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.order.FsmOrder;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.orderstateevent.OrderStateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author will
 * @date 2021/4/28 9:35
 *
 * 状态机引擎具体是怎么把这个过程串联起来?
 *  初始化阶段和运行时阶段。
 *
 *  运行时阶段
 */
//@Componet
public class DefaultOrderFsmExecutor implements OrderFsmExecutor{

    private DefaultStateProcessRegistry stateProcessRegistry;

    /**
     * 1 针对同一个订单维度加锁（redis分布式锁）、同一时间只允许有一个状态变更操作进行，其他请求则进行排队等待
     * 2 在数据库层对当前state做校验、类似与乐观锁方式。最终是将其他请求抛错、由上游业务进行处理
     *
     * 针对上面2个做业务幂等
     * @param orderStateEvent
     * @param <T>
     * @return
     * @throws Exception
     */
    @Override
    public <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent) throws Exception {
        FsmOrder fsmOrder = null;
        if (orderStateEvent.newCreate()) {
            //fsmOrder = this.fsmOrderService.getFsmOrder(orderStateEvent.getOrderId());
            if (fsmOrder == null) {
                //throw new FsmException(ErrorCodeEnum.ORDER_NOT_FOUND);
            }
        }
        return sendEvent(orderStateEvent, fsmOrder);
    }
    @Override
    public <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) throws Exception {
        // 构造当前事件上下文
        StateContext context = this.getStateContext(orderStateEvent, fsmOrder);
        // 获取当前事件处理器
        List<AbstractStateProcessor<T, ?>> stateProcessor = this.getStateProcessor(context);
        List<AbstractStateProcessor> processorResult = new ArrayList<>(stateProcessor.size());
        // TODO 检测到多个状态执行器怎么处理?
        // 根据上下文获取唯一的业务处理器
        for (AbstractStateProcessor processor : stateProcessor) {
            if (processor.filter(context)) {
                processorResult.add(processor);
            }
        }
        if(processorResult.size() > 1){
            //throw new FsmException(ErrorCodeEnum.ORDER_NOT_FOUND);
        }
        // 执行处理逻辑
        return processorResult.get(0).action(context);
    }
    private <T> List<AbstractStateProcessor<T, ?>> getStateProcessor(StateContext<?> context) {
        OrderStateEvent stateEvent = context.getOrderStateEvent();
        FsmOrder fsmOrder = context.getFsmOrder();
        // 根据状态+事件对象获取所对应的业务处理器集合
        List<AbstractStateProcessor> processorResult = stateProcessRegistry.acquireStateProcess(fsmOrder.getOrderState(),
                stateEvent.getEventType(), fsmOrder.bizCode(), fsmOrder.sceneId());
        if (processorResult == null) {
            // 订单状态发生改变
            if (!Objects.isNull(stateEvent.orderState()) && !stateEvent.orderState().equals(fsmOrder.getOrderState())) {
                //throw new FsmException(ErrorCodeEnum.ORDER_STATE_NOT_MATCH);
            }
            //throw new FsmException(ErrorCodeEnum.NOT_FOUND_PROCESSOR);
        }
        /*if (CollectionUtils.isEmpty(processorResult)) {
            throw new FsmException(ErrorCodeEnum.NOT_FOUND_PROCESSOR);
        }
        if (processorResult.size() > 1) {
            throw new FsmException(ErrorCodeEnum.FOUND_MORE_PROCESSOR);
        }*/
        //return processorResult;
        return null;
    }
    private StateContext<?> getStateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) {
        StateContext<?> context = new StateContext(orderStateEvent, fsmOrder);
        return context;
    }
}
