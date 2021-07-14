package com.zyblue.fastim.common.enumeration;

/**
 * @author will
 * @date 2021/7/12 17:46
 */
public enum DataType {
    TEXT((byte) 1, "文本"),
    PIC((byte) 2, "图片链接"),
    FILE((byte) 3, "文件链接");

    DataType(byte val, String desc) {
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
