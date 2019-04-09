package com.example.lib_common.base.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/2
 * @time : 14:57
 * @email : 15869107730@163.com
 * @note :
 */
public class UserSleepBean implements Serializable {
    private Integer uid;
    private String nickname;
    private List<SleepBean> sleepBeans;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<SleepBean> getSleepBeans() {
        return sleepBeans;
    }

    public void setSleepBeans(List<SleepBean> sleepBeans) {
        this.sleepBeans = sleepBeans;
    }
}
