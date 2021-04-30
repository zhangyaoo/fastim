package com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder;

import com.zyblue.fastim.common.mytest.designpatters.statebuilder.check.Checkable;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.check.Checker;
import com.zyblue.fastim.common.mytest.designpatters.statebuilder.check.CheckerExecutor;

import java.util.List;

/**
 * @author will
 * @date 2021/4/27 10:30
 *
 * 订单业务状态机引擎
 *
 * 业务逻辑：校验、业务逻辑执行、数据更新持久化；
 * 再次抽象，可以将一个状态流转分为数据准备（prepare）——>校验（check）——>获取下一个状态（getNextState）——>业务逻辑执行（action）——>数据持久化（save）——>后续处理（after）这六个阶段
 */
public abstract class AbstractStateProcessor<T, C> implements StateProcessor<T,C>, StateActionStep<T,C>{


    private CheckerExecutor checkerExecutor;

    /**
     * 模板方法，抽象类
     */
    @Override
    public final ServiceResult<T> action(StateContext<C> context) throws Exception {
        ServiceResult<T> result = null;
        Checkable checkable = this.getCheckable(context);
        try {
            List<Checker> paramChecker = checkable.getParamChecker();
            ServiceResult serviceResult = checkerExecutor.serialCheck(paramChecker, context);
            if (!result.isSuccess()) {
                return result;
            }
            // 数据准备
            this.prepare(context);
            // 串行校验器
            result = checkerExecutor.serialCheck(checkable.getSyncChecker(), context);
            if (!result.isSuccess()) {
                return result;
            }
            // 并行校验器
            result = checkerExecutor.parallelCheck(checkable.getAsyncChecker(), context);
            if (!result.isSuccess()) {
                return result;
            }
            // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
            String nextState = this.getNextState(context);
            // 业务逻辑
            result = this.action(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // 持久化
            result = this.save(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // after
            this.after(context);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
