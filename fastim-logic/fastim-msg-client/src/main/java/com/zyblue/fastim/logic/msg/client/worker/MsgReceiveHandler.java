package com.zyblue.fastim.logic.msg.client.worker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author will
 * @date 2022/4/13 11:11
 * 方法执行模型
 */
public class MsgReceiveHandler {

    private static final Logger logger = LogManager.getLogger(MsgReceiveHandler.class);

    protected final Object IMPLEMENT;

    protected final Method METHOD;

    public MsgReceiveHandler(Object implement, Method method) {
        IMPLEMENT = implement;
        METHOD = method;
    }

    public void execute(List<Map<String, Object>> data){
        try {
            METHOD.invoke(IMPLEMENT, data);
        }catch (Exception e){
            logger.error(e);
        }
    }
}
