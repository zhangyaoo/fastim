package com.zyblue.fastim.client.constant;

import com.zyblue.fastim.client.service.ImService;
import com.zyblue.fastim.client.service.impl.GroupChatServiceImpl;
import com.zyblue.fastim.client.service.impl.HeartbeatServiceImpl;
import com.zyblue.fastim.client.service.impl.PushServiceImpl;
import com.zyblue.fastim.client.service.impl.SingleChatServiceImpl;

/**
 * @author will
 * @date 2021/7/12 17:45
 * 心跳、推送、单聊、群聊
 */
public enum CmdType {
    /**
     * 单聊
     */
    SINGLE_CHAT((byte) 1, "单聊", SingleChatServiceImpl.class),
    /**
     * 群聊
     */
    GROUP_CHAT((byte) 2, "群聊", GroupChatServiceImpl.class),
    /**
     * 心跳
     */
    HEARTBEAT((byte) 3, "心跳",  HeartbeatServiceImpl.class),
    /**
     * 推送
     */
    PUSH((byte) 4, "推送", PushServiceImpl.class);

    CmdType(byte val, String desc, Class<? extends ImService<?>> clazz) {
        this.val = val;
        this.desc = desc;
        this.clazz = clazz;
    }

    public static CmdType getCmdTypeByVal(byte val){
        for (CmdType cmdType : CmdType.values()) {
            if (val == cmdType.getVal()) {
                return cmdType;
            }
        }
        return null;
    }

    private byte val;

    private String desc;

    private Class<? extends ImService<?>> clazz;

    public byte getVal() {
        return val;
    }

    public void setVal(byte val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Class<? extends ImService<?>> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends ImService<?>> clazz) {
        this.clazz = clazz;
    }
}
