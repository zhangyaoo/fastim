package com.zyblue.fastim.common.constant;

public enum ChannelAttr {
    TOKEN("token", "登录凭证");

    private String val;

    private String desc;

    ChannelAttr(String val, String desc){
        this.val= val;
        this.desc=desc;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
