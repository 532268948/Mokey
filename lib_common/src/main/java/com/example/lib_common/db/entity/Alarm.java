package com.example.lib_common.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author: tianhuaye
 * @date: 2019/1/28 15:54
 * @description: 闹钟
 */
@Entity
public class Alarm {
    @Id
    private Long id;
    @Property
    private Integer hour;
    @Property
    private Integer minute;
    @Property
    private Integer mode;
    @Property
    private String ringPath;
    @Property
    private String msg;
    @Property
    private Integer type;
    @Property
    private boolean open;

    @Generated(hash = 1634779579)
    public Alarm(Long id, Integer hour, Integer minute, Integer mode,
                 String ringPath, String msg, Integer type, boolean open) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.mode = mode;
        this.ringPath = ringPath;
        this.msg = msg;
        this.type = type;
        this.open = open;
    }

    @Generated(hash = 1972324134)
    public Alarm() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHour() {
        return this.hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return this.minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getMode() {
        return this.mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getRingPath() {
        return this.ringPath;
    }

    public void setRingPath(String ringPath) {
        this.ringPath = ringPath;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }


    @Override
    public String toString() {
        return "id:" + id + " " +
                "hour:" + hour + " " +
                "minute:" + minute + " " +
                "mode" + mode + " " +
                "ringPath" + ringPath + " " +
                "msg:" + msg + " " +
                "type:" + type + " " +
                "open:" + open;

    }
}
