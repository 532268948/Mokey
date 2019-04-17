package com.example.lib_common.bean;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/13
 * @time : 20:17
 * @email : 15869107730@163.com
 * @note : 接口返回数据统一格式
 */
public class ResponseWrapper<T> {
    /**
     * 状态码
     */
    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
}
