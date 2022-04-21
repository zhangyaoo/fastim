package com.zyblue.fastim.logic.msg.client.annotation;

import java.lang.annotation.*;

/**
 * @author will
 * @date 2022/4/13 10:54
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MsgReceivedListener {

}
