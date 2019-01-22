package com.example.lib_common.base.bean;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/13
 * @time : 20:17
 * @email : 15869107730@163.com
 * @note : 接口返回数据统一格式
 */
public class ResponseWrapper<T extends BaseItem> {
    /**
     * 状态码
     */
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
