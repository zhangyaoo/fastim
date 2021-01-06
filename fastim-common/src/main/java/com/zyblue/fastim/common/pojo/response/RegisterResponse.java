package com.zyblue.fastim.common.pojo.response;

import java.io.Serializable;

public class RegisterResponse implements Serializable {


    private static final long serialVersionUID = 6377606297678069156L;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "userId=" + userId +
                '}';
    }
}
