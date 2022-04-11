package com.zyblue.fastim.common.pojo.message;

import java.time.LocalDateTime;

/**
 * @author will
 * @date 2021/11/29 14:34
 */
public class SingleChatNotify implements MsgRequest{
    /**
     * 会话ID
     */
    private Long sessionId;

    private Long fromUid;

    private Long toUid;

    /**
     * 消息发送成功的消息ID
     */
    private Long msgId;

    /**
     * 消息类型 DataType
     */
    private byte contentType;

    private String content;

    private LocalDateTime sendTime;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getFromUid() {
        return fromUid;
    }

    public void setFromUid(Long fromUid) {
        this.fromUid = fromUid;
    }

    public Long getToUid() {
        return toUid;
    }

    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public byte getContentType() {
        return contentType;
    }

    public void setContentType(byte contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
