package com.example.module_habit.bean;

import com.example.lib_common.bean.SleepPrepareItem;

/**
 * @author: tianhuaye
 * @date: 2019/1/2 13:01
 * @description:
 */
public class PrepareBean {
    private float time;
    private SleepPrepareItem sleepPrepareItem;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public SleepPrepareItem getSleepPrepareItem() {
        return sleepPrepareItem;
    }

    public void setSleepPrepareItem(SleepPrepareItem sleepPrepareItem) {
        this.sleepPrepareItem = sleepPrepareItem;
    }

    @Override
    public String toString() {
        return "PrepareBean{" +
                "time=" + time +
                ", sleepPrepareItem=" + sleepPrepareItem.toString() +
                '}';
    }
}
