package com.zyblue.fastim.client.event;

import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.pojo.message.MsgRequest;

/**
 * @author will
 * @date 2021/12/3 15:57
 */
public class NewMsgEvent {
    private FastImMsg msg;

    private MsgRequest data;

    public NewMsgEvent(FastImMsg msg, MsgRequest data) {
        this.msg = msg;
        this.data = data;
    }

    public MsgRequest getData() {
        return data;
    }

    public void setData(MsgRequest data) {
        this.data = data;
    }

    public FastImMsg getMsg() {
        return msg;
    }

    public void setMsg(FastImMsg msg) {
        this.msg = msg;
    }
}
