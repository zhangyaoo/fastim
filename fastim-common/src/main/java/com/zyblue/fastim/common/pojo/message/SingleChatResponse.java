package com.zyblue.fastim.common.pojo.message;

/**
 * @author will
 * @date 2021/7/28 16:34
 */
public class SingleChatResponse {
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
