package com.zyblue.fastim.common.pojo.request;

/**
 * @author will
 * @date 2021/4/30 10:53
 */
public class MsgRequest{

    private String token;

    private String msg;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
