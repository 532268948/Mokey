package com.example.lib_common.base.bean.request;

import java.io.Serializable;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/9
 * @time : 14:32
 * @email : 15869107730@163.com
 * @note :
 */
public class TurnBean implements Serializable {

    private static final long serialVersionUID = 8766263034792545111L;
    long time;
    int level;

    public TurnBean(long time, int level) {
        this.time = time;
        this.level = level;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
