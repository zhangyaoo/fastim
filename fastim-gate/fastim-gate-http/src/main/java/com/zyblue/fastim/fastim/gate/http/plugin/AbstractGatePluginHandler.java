package com.zyblue.fastim.fastim.gate.http.plugin;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

/**
 * @author will
 * @date 2021/6/15 9:51
 */
public abstract class AbstractGatePluginHandler implements GatePluginHandler{

    private void responseContent(ChannelHandlerContext ctx, String json) {
        ByteBuf buf = Unpooled.wrappedBuffer(json.getBytes(StandardCharsets.UTF_8));
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        buildHeader(response);
        ctx.writeAndFlush(response);
    }

    private void buildHeader(DefaultFullHttpResponse response) {

        HttpHeaders headers = response.headers();
        headers.setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        /*headers.set(HttpHeaderNames.CONTENT_TYPE, Http);

        List<Cookie> cookies = cicadaResponse.cookies();
        for (Cookie cookie : cookies) {
            headers.add(CicadaConstant.ContentType.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookie));
        }*/
    }
}
