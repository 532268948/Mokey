package com.example.lib_common.base.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/23
 * @time : 16:15
 * @email : 15869107730@163.com
 * @note : 睡前小曲
 */
@Entity
public class MusicBefore {
    @Id
    private Long id;
    @Property
    private int type;
    @Property
    private String name;
    @Property
    private String author;
    @Property
    private String cover;
    @Property
    private int free;
    @Property
    private float price;
    @Property
    private String during;
    @Property
    private String resource;
    @Property
    private Long playTimes;
    @Property
    private int status;

    @Generated(hash = 739082339)
    public MusicBefore(Long id, int type, String name, String author, String cover,
            int free, float price, String during, String resource, Long playTimes,
            int status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.author = author;
        this.cover = cover;
        this.free = free;
        this.price = price;
        this.during = during;
        this.resource = resource;
        this.playTimes = playTimes;
        this.status = status;
    }
    @Generated(hash = 2131086650)
    public MusicBefore() {
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCover() {
        return this.cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public int getFree() {
        return this.free;
    }
    public void setFree(int free) {
        this.free = free;
    }
    public float getPrice() {
        return this.price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getResource() {
        return this.resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDuring() {
        return during;
    }

    public void setDuring(String during) {
        this.during = during;
    }

    public Long getPlayTimes() {
        return this.playTimes;
    }
    public void setPlayTimes(Long playTimes) {
        this.playTimes = playTimes;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
