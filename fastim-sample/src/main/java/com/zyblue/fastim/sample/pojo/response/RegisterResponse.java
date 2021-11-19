package com.zyblue.fastim.sample.pojo.response;

import java.io.Serializable;

public class RegisterResponse implements Serializable {

    private static final long serialVersionUID = 6377606297678069156L;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
