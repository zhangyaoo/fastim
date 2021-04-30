package com.zyblue.fastim.common.codec;

/**
 * @author will
 * @date 2021/4/30 14:29
 */
public enum InvocationType {
    CUSTOM_TCP(1, "自定义tcp"),
    HTTP1(2, "http1"),
    HTTP2(3, "http2");

    private Integer val;

    private String desc;

    InvocationType(Integer val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
