package com.zyblue.fastim.router;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FastimRouterApplicationTests {

    @Test
    void contextLoads() {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20881/com.zyblue.fastim.fastim.gate.tcp.service.RouterMsgService");
        reference.setInterface("com.zyblue.fastim.fastim.gate.tcp.service.RouterMsgService");
        reference.setGeneric("true");
        reference.setCheck(true);
        GenericService genericService = reference.get();
        Object routerMsg = genericService.$invoke("routerMsg", null, null);
        System.out.println("result:" + routerMsg);
    }
}
