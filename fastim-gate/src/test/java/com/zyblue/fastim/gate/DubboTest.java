package com.zyblue.fastim.gate;

import com.google.common.collect.Maps;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/**
 * @author will
 * @date 2020/12/23 11:32
 */
@SpringBootTest(classes = FastimGateApplication.class)
public class DubboTest {
    private final static Logger logger = LoggerFactory.getLogger(DubboTest.class);

    @Test
    public void test(){
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("fastim-gate");
        reference.setApplication(applicationConfig);
        reference.setInterface("com.zyblue.fastim.logic.service.TestService");
        reference.setGeneric("true");
        reference.setUrl("dubbo://127.0.0.1:29527");
        reference.setRetries(0);
        reference.setTimeout(3000000);
        GenericService genericService = reference.get();


        HashMap<String, Object> map = Maps.newHashMap();
        //map.put("class", "com.zyblue.fastim.common.pojo.bo.User");
        map.put("name", "ab");
        map.put("birthday_first", "1990-10-10");
        map.put("birthday_end", "1990-10-10 00:00:00");
        map.put("boy", "true");
        map.put("number1", "123");
        map.put("number2", "123.2");
        map.put("number3", "123.1");
        map.put("number4", "123");


        /*Object sayHi = genericService.$invoke("sayHi", new String[]{"java.lang.String", "java.lang.String", "java.lang.String", "java.util.HashMap"},
                new Object[]{"1990-10-10", "1990-10-10 00:11:00", "1", map});*/
        Object sayHi1 = genericService.$invoke("sayHi1", new String[]{"java.util.HashMap"},
                new Object[]{map});
        logger.info("sayHi1:{}", sayHi1);
        /*Object sayHi2 = genericService.$invoke("sayHi2", new String[]{"java.lang.String", "java.lang.String", "java.lang.String"},
                new Object[]{"1990-10-10", "1990-10-10 00:11:00", "1"});*/
    }
}
