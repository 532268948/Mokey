package com.example.lib_common.base.bean.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/23
 * @time : 18:12
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicBean implements Serializable {
    private Long id;
    private String name;
    private String author;
    private String cover;
    private Integer type;
    private Integer free;
    private BigDecimal price;
    private String resource;
    private String during;
    private Long playTimes;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getResource() {
        return resource;
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
        return playTimes;
    }

    public void setPlayTimes(Long playTimes) {
        this.playTimes = playTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
