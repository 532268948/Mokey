package com.example.lib_common.base.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: tianhuaye
 * date:   2018/11/15 15:34
 * description:
 */
@Entity
public class User {

    @Id
    private Long id;
    @Property
    private String name;
    @Property
    private String nickName;
    @Generated(hash = 1824209997)
    public User(Long id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
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
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
}
