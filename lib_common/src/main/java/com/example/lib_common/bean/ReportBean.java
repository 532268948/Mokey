package com.example.lib_common.bean;

import android.text.TextUtils;

import com.example.lib_common.bean.response.SleepBean;
import com.example.lib_common.common.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 11:05
 * @description:
 */
public class ReportBean extends BaseItem implements Serializable {
    private static final long serialVersionUID = -7909751988804147464L;
    /**
     * 用户id
     */
    private int uid;
    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 睡眠分数
     */
    private int grade;
    /**
     * 入睡时间
     */
    private long startTime;
    /**
     * 结束时间
     */
    private long endTime;
    /**
     * 睡眠质量数据
     */
    private List<QualityBean> qualityBeans;

    public ReportBean(SleepBean sleepBean, String nickname) {
        if (sleepBean == null) {
            return;
        }

        this.itemType = Constant.ItemType.SLEEP_REPORT;
        this.grade = sleepBean.getGrade() == null ? 0 : sleepBean.getGrade();
        this.nickname = nickname;
        this.startTime = sleepBean.getStart_time();
        this.endTime = sleepBean.getEnd_time();

        if (sleepBean.getDetail() != null) {
            String[] str = sleepBean.getDetail().split("", -1);
            if (qualityBeans == null) {
                qualityBeans = new ArrayList<>();
            }
            for (String grade : str) {
                if (TextUtils.isEmpty(grade)) {
                    continue;
                }
                QualityBean qualityBean = new QualityBean();
                int g = 0;
                try {
                    g = Integer.valueOf(grade);
                } catch (Exception e) {
                    g = 0;
                }
                if (g > 0 && g <= 3) {
                    qualityBean.setType(Constant.SleepQuality.SLEEP_DEEP);
                    qualityBean.setGrade(g);
                } else if (g > 3 && g <= 8) {
                    qualityBean.setType(Constant.SleepQuality.SLEEP_SHALLOW);
                    qualityBean.setGrade(g);
                } else if (g > 8) {
                    qualityBean.setType(Constant.SleepQuality.SLEEP_DREAM);
                    qualityBean.setGrade(g);
                } else {
                    qualityBean.setType(Constant.SleepQuality.SLEEP_ERROR);
                    qualityBean.setGrade(g);
                }
                qualityBeans.add(qualityBean);
            }
        }
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<QualityBean> getQualityBeans() {
        return qualityBeans;
    }

    public void setQualityBeans(List<QualityBean> qualityBeans) {
        this.qualityBeans = qualityBeans;
    }
}
