package com.example.lib_common.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * author: tianhuaye
 * date:   2018/11/15 15:34
 * description: 用户
 */
@Entity
public class User {

    @Id
    private Long id;
    @Property
    private String name;
    @Property
    private String password;
    @Property
    private String nickname;
    @Property
    private String phone;
    @Property
    private String head;
    @Property
    private Long loginTime;
    @Property
    private int gender;

    @Generated(hash = 479324520)
    public User(Long id, String name, String password, String nickname,
                String phone, String head, Long loginTime, int gender) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.head = head;
        this.loginTime = loginTime;
        this.gender = gender;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHead() {
        return this.head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
