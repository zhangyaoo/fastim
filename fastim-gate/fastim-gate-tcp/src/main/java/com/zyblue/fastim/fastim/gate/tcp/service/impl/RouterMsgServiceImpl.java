package com.zyblue.fastim.fastim.gate.tcp.service.impl;

import com.zyblue.fastim.fastim.gate.tcp.service.RouterMsgService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author will
 * @date 2021/6/24 14:10
 */
@Service
@org.springframework.stereotype.Service
public class RouterMsgServiceImpl implements RouterMsgService {
    @Override
    public Object routerMsg() {
        return "ok";
    }
}
