package com.zyblue.fastim.fastim.gate.tcp.manager;

import io.netty.channel.Channel;

import java.time.LocalDateTime;

/**
 * @author will
 * @date 2022/4/12 17:15
 * 会话信息
 */
public class InstanceOutSession {

    private Channel clientChannel;

    private LocalDateTime lastReceivedTime;

    private LocalDateTime secretKey;

    private Long userId;


}
