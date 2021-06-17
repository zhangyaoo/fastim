package com.zyblue.fastim.fastim.gate.http.config;

import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;

/**
 * @author will
 * @date 2021/6/8 13:02
 */
public class CorsHandlerConfig {
    private static volatile CorsConfig config = null;

    public static CorsConfig getInstance(){
        if(config == null){
            synchronized (CorsHandlerConfig.class){
                if(config == null){
                    CorsConfigBuilder corsConfigBuilder = CorsConfigBuilder.forAnyOrigin();
                    String allowHeaders = "DNT,web-token,app-token,Authorization,Accept,Origin,Keep-Alive,User-Agent,X-Mx-ReqToken,X-Data-Type,X-Auth-Token,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range,X-Custom-Header";
                    corsConfigBuilder.allowedRequestHeaders(allowHeaders);
                    corsConfigBuilder.allowCredentials();
                    corsConfigBuilder.maxAge(1800L);
                    config = corsConfigBuilder.build();
                }
            }
        }
        return config;
    }
}
