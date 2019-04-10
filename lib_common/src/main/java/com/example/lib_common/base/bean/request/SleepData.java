package com.example.lib_common.base.bean.request;

import java.io.Serializable;
import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/10
 * @time : 10:50
 * @email : 15869107730@163.com
 * @note :
 */
public class SleepData implements Serializable {
    private static final long serialVersionUID = -5408986311871620487L;
    /**
     * 睡眠开始时间
     */
    private long start_time;
    /**
     * 睡眠结束时间
     */
    private long end_time;
    /**
     * 睡眠过程中解锁次数
     */
    private int unLockTimes;
    /**
     * 睡眠过程翻身数据
     */
    private List<TurnBean> turns;

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getUnLockTimes() {
        return unLockTimes;
    }

    public void setUnLockTimes(int unLockTimes) {
        this.unLockTimes = unLockTimes;
    }

    public List<TurnBean> getTurns() {
        return turns;
    }

    public void setTurns(List<TurnBean> turns) {
        this.turns = turns;
    }
}
