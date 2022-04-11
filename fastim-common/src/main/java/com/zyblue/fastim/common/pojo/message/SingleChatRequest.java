package com.zyblue.fastim.common.pojo.message;

import java.time.LocalDateTime;

/**
 * @author will
 * @date 2021/7/19 17:06
 */
public class SingleChatRequest implements MsgRequest {
    private Long fromUid;

    private Long toUid;

    private String content;

    private byte contentType;

    private LocalDateTime sendTime;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte getContentType() {
        return contentType;
    }

    public void setContentType(byte contentType) {
        this.contentType = contentType;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
