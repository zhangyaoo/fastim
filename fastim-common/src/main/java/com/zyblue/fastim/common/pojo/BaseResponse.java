package com.zyblue.fastim.common.pojo;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -800974757695429116L;

    private int code;
    private String msg;
    private T data;

    public BaseResponse(){

    }

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
