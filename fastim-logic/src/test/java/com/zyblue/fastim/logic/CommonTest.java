package com.zyblue.fastim.logic;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author will
 * @date 2021/8/10 17:15
 */
@SpringBootTest
public class CommonTest {

    @Test
    public void test() throws InterruptedException {
        // ReferenceConfig 封装了所有与注册中心及服务提供方连接
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20881");
        reference.setInterface("com.zyblue.fastim.router.service.RouterMsgService");
        reference.setGeneric("true");
        reference.setCheck(true);
        GenericService genericService = reference.get();
        CompletableFuture<Object> future = genericService.$invokeAsync("routerMsgVoid", null, null);
        future.whenComplete((result, throwable) -> {
            System.out.println("result:" + result);

            System.out.println("result:" + result.toString());

        });
        System.out.println("result over");
        while (true){
            TimeUnit.SECONDS.sleep(10);
        }
    }
}
