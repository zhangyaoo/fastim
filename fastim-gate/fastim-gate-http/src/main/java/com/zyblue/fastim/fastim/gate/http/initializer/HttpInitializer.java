package com.zyblue.fastim.fastim.gate.http.initializer;

import com.zyblue.fastim.fastim.gate.http.config.CorsHandlerConfig;
import com.zyblue.fastim.fastim.gate.http.handler.HttpHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author will
 * @date 2021/6/7 23:00
 */
public class HttpInitializer extends ChannelInitializer<Channel> {
    private final HttpHandler httpHandler = new HttpHandler();

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                // TODO .addFirst(new SslHandler())
                .addLast(new HttpRequestDecoder())
                .addLast(new HttpResponseEncoder())
                .addLast(new ChunkedWriteHandler())
                .addLast(new CorsHandler(CorsHandlerConfig.getInstance()))
                .addLast(httpHandler)
                .addLast("logging", new LoggingHandler(LogLevel.INFO));
    }
}
