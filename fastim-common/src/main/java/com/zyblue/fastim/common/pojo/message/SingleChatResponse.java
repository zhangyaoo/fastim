package com.zyblue.fastim.common.pojo.message;

/**
 * @author will
 * @date 2021/7/28 16:34
 */
public class SingleChatResponse {
    /**
     * 会话ID
     */
    private Long sessionId;
    /**
     * 消息发送成功的消息ID
     */
    private Long msgId;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }
}
