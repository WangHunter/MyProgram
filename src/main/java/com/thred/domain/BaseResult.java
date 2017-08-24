package com.thred.domain;

/**
 * @Description:
 * @Author: yangzl2008
 * @Date: 2016/1/9 19:07
 */
public class BaseResult {
    private int code;
    private String msg;

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

    @Override
    public String toString() {
        return "BaseResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
