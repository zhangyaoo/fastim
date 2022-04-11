package com.zyblue.fastim.common.pojo.message;

/**
 * @author will
 * @date 2021/12/8 17:45
 */
public class SingleChatNotifyResponse {
    /**
     * 消息发送成功的消息ID
     */
    private Long msgId;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }
}
