package com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder;

/**
 * @author will
 * @date 2021/4/27 10:35
 */
public interface StateProcessor<T, C> {
    ServiceResult<T> action(StateContext<C> context) throws Exception;
}
