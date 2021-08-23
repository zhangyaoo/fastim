package com.zyblue.fastim.fastim.gate.http.plugin;

import com.zyblue.fastim.fastim.gate.http.plugin.chain.HttpHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author will
 * @date 2021/4/29 18:19
 *
 * 鉴权处理
 */
@Component
public class GateAuthHandler implements GatePluginHandler{
    private final static Logger logger = LoggerFactory.getLogger(GateAuthHandler.class);

    @Override
    public void execute(HttpHandlerContext context, DefaultHttpRequest request) {
        logger.info("GateAuthHandler execute");
    }

    @Override
    public void response(HttpHandlerContext context, DefaultHttpRequest request, DefaultFullHttpResponse response) {
        logger.info("GateAuthHandler response");

    }
}
