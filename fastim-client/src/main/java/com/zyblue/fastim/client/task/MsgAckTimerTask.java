package com.zyblue.fastim.client.task;

import com.zyblue.fastim.client.manager.MsgManager;
import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author will
 * @date 2021/7/8 14:34
 */
public class MsgAckTimerTask implements TimerTask {
    private final static Logger logger = LoggerFactory.getLogger(MsgAckTimerTask.class);

    /**
     * 客户端channel
     */
    private final NioSocketChannel channel;

    private final Integer sendDuration;

    private Integer sendRetry;

    private final FastImMsg msg;

    public MsgAckTimerTask(NioSocketChannel channel, Integer sendDuration, Integer sendRetry, FastImMsg msg) {
        this.channel = channel;
        this.sendDuration = sendDuration;
        this.sendRetry = sendRetry;
        this.msg = msg;
    }

    @Override
    public void run(Timeout timeout) {
        int sequenceId = msg.getSequenceId();
        logger.info("msg resend, sequenceId:{}, sendRetry:{}", sequenceId, sendRetry);
        if(timeout.isCancelled()){
            if(MsgManager.ACK_MSG_TIMEOUT_LIST.containsKey(sequenceId)) {
                MsgManager.ACK_MSG_TIMEOUT_LIST.remove(sequenceId);
            }
            return;
        }

        // 还未回ack
        if(MsgManager.ACK_MSG_TIMEOUT_LIST.containsKey(sequenceId)){
            if(sendRetry > 0){
                channel.writeAndFlush(msg).addListener(future ->{
                    if(future.isSuccess()){
                        timeout.timer().newTimeout(new MsgAckTimerTask(channel, sendDuration, --sendRetry, msg), sendDuration, TimeUnit.MILLISECONDS);
                    }
                });
            }else {
                MsgManager.ACK_MSG_TIMEOUT_LIST.remove(sequenceId);
                timeout.cancel();
            }

        }
        // 已回ack
        else {
            // cancel or return
            timeout.cancel();
        }
    }
}

