package com.zyblue.fastim.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zyblue.fastim.common.constant.MessageType;
import com.zyblue.fastim.common.util.JacksonUtils;

import java.io.Serializable;

/**
 * 消息实体类
 *
 * @author will
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message<T> implements Serializable {

    /**
     * @see MessageType
     */
    private String messageType;

    /**
     * 业务数据
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    private T data;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        try {
            return JacksonUtils.obj2json(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
