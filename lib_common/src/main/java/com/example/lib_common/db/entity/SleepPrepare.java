package com.example.lib_common.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author: tianhuaye
 * @date: 2019/1/30 13:00
 * @description: 睡前准备清单
 */
@Entity
public class SleepPrepare {

    @Id
    private Long id;
    @Property
    private Integer checkNum;

    @Generated(hash = 1436231049)
    public SleepPrepare(Long id, Integer checkNum) {
        this.id = id;
        this.checkNum = checkNum;
    }

    @Generated(hash = 1212198923)
    public SleepPrepare() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCheckNum() {
        return this.checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

}
