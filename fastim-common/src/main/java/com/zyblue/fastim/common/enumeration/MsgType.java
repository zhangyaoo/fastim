package com.zyblue.fastim.common.enumeration;

/**
 * @author will
 * @date 2021/7/12 17:46
 */
public enum MsgType {

    REQUEST((byte) 1, "客户端请求包"),
    RESPONSE((byte) 2, "响应包"),
    NOTIFY((byte) 3, "服务端通知包");

    MsgType(byte val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    private byte val;

    private String desc;

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
}
