package com.zyblue.fastim.fastim.gate.http.plugin.chain;

import com.zyblue.fastim.fastim.gate.http.plugin.GatePluginHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.util.ReferenceCountUtil;

/**
 * @author will
 * @date 2021/6/7 23:29
 * 插件处理上下文
 */
public class HttpHandlerContext {
    /**
     * 下一节点
     */
    private HttpHandlerContext next;

    /**
     * 上一节点
     */
    private HttpHandlerContext prev;

    /**
     * 上下文所属的handler
     */
    private final GatePluginHandler handler;

    /**
     * 第一个上下文的所属的context
     */
    private ChannelHandlerContext channelHandlerContext;

    public HttpHandlerContext(GatePluginHandler handler){
        this.handler = handler;
    }

    public void fireHandle(DefaultHttpRequest request) {
        if (this.next != null) {
            this.next.handler.execute(this, request);
        }
    }

    public void fireResult(DefaultHttpRequest request, DefaultFullHttpResponse response){

        if (this.prev != null) {
            this.prev.handler.response(this, request, response);
        }else {
            channelHandlerContext.writeAndFlush(response);
            int refCnt = response.refCnt();
            if(refCnt > 0){
                ReferenceCountUtil.release(response, refCnt);
            }
        }
    }

    public void execute(DefaultHttpRequest request){
        this.handler.execute(this, request);
    }

    public void setNext(HttpHandlerContext next) {
        this.next = next;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }
}
