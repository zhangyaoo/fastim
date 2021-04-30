package com.zyblue.fastim.common.mytest.designpatters.statebuilder.stateprocessor;

import com.zyblue.fastim.common.mytest.designpatters.statebuilder.annotion.OrderProcessor;

/**
 * @author will
 * @date 2021/4/27 18:06
 */
@OrderProcessor(state = "init", bizCode = "code1", sceneId = "scene2",event = "event2")
public class StateCreateProcessor2 extends StateCreateProcessor {

    @Override
    protected String doPromotion() {
        return "StateCreateProcessor2 特有逻辑";
    }
}
