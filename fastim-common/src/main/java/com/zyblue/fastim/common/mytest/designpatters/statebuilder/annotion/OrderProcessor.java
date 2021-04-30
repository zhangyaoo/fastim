package com.zyblue.fastim.common.mytest.designpatters.statebuilder.annotion;

import java.lang.annotation.*;

/**
 * @author will
 * @date 2021/4/27 10:33
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OrderProcessor {
    /**
     * 指定状态，state不能同时存在
     */
    String[] state() default {};
    /**
     * 业务
     */
    String[] bizCode() default {};
    /**
     * 场景
     */
    String[] sceneId() default {};
    /**
     * 订单操作事件
     */
    String event();
}
