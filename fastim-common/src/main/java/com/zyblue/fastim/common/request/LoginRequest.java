package com.zyblue.fastim.common.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 5422016176211152480L;

    private Long userId;

    private String mobile;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userId=" + userId +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
