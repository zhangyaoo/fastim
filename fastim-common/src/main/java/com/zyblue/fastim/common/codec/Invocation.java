package com.zyblue.fastim.common.codec;

/**
 * @author will
 * @date 2021/4/30 11:24
 *
 * 通用协议消息体
 */
public class Invocation<T> {
    /**
     * 类型
     */
    private Integer type;
    /**
     * 消息，JSON 格式
     */
    private T message;

    public Invocation(Integer type, T message) {
        this.type = type;
        this.message = message;
    }

    public Invocation(){

    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
