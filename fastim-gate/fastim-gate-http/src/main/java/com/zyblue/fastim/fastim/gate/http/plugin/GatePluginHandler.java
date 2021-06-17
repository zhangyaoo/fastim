package com.zyblue.fastim.fastim.gate.http.plugin;


import com.zyblue.fastim.fastim.gate.http.plugin.chain.HttpHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;

/**
 * @author will
 * @date 2021/6/7 23:35
 */
public interface GatePluginHandler {
    /**
     * 执行
     */
    void execute(HttpHandlerContext context, DefaultHttpRequest request);

    /**
     * 执行返回
     */
    void response(HttpHandlerContext context, DefaultHttpRequest request, DefaultFullHttpResponse response);
}
