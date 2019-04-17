package com.example.lib_common.bean.response;

import java.io.Serializable;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/13
 * @time : 20:31
 * @email : 15869107730@163.com
 * @note :
 */
public class LoginItem implements Serializable {

    /**
     * head :
     * gender : 0
     * phone : 15869107730
     * name : 15869107730
     * id : 1
     */

    private String head;
    private Integer gender;
    private String phone;
    private String nickname;
    private String name;
    private Long id;
    private String token;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
