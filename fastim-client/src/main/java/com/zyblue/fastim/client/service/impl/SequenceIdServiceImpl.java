package com.zyblue.fastim.client.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author will
 * @date 2021/7/9 16:55
 *
 * 客户端本地序列化生成服务，只需要全局用户递增，只需要单用户递增即可
 */
public class SequenceIdServiceImpl {
    public static int generateSequenceId(){

        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        return 0;
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        Long second = now.toEpochSecond(ZoneOffset.of("+8"));
        Long second2 = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();

        System.out.println(second);
        System.out.println(second2);

        Integer a = 999999999;
        Integer b = 2099999999;
        Integer c = 2147483647;
        Integer d = 1626244883;

        Integer f = 1 << 30 - 1;
        Integer e = f/3600/24/365;
        System.out.println(f);
        int i = 0x7fffffff / 3600 / 24 / 365;
        System.out.println(i);


        System.out.println(e);


    }
}
