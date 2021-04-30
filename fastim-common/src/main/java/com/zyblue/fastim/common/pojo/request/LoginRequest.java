package com.zyblue.fastim.common.pojo.request;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 5422016176211152480L;

    private String mobile;

    private String pwd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
