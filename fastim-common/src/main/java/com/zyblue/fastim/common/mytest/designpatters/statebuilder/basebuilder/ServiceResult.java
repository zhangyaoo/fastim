package com.zyblue.fastim.common.mytest.designpatters.statebuilder.basebuilder;

/**
 * @author will
 * @date 2021/4/27 10:20
 */
public class ServiceResult<T> {
    boolean success;

    Object data;

    String message;

    public ServiceResult() {
    }

    public ServiceResult(String data, String message){
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
