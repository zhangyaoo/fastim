package com.zyblue.fastim.fastim.gate.websocket.server;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

/**
 * @author will
 * @date 2021/12/3 9:32
 * ServerEndpoint注解代表多例， 每次新建连接都会实例化该类
 */
@ServerEndpoint(readerIdleTimeSeconds = "9", port = "${server.port:8999}", maxFramePayloadLength = "209715200")
public class FastImWebsocketServer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers) {
        //
    }

    @OnClose
    public void onClose(Session session) {
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            // 9秒没有收到客户端的
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("reader idle exceed 9s,connection closed");
                session.close();
            }
        }
    }
}
