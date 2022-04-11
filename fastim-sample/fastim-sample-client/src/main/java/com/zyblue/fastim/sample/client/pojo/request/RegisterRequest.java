package com.zyblue.fastim.sample.client.pojo.request;

import java.io.Serializable;

public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = -6561848170760392546L;

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
        return "RegisterRequest{" +
                "mobile='" + mobile + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
