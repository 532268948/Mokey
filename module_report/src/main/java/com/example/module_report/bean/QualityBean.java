package com.example.module_report.bean;

import java.io.Serializable;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 13:52
 * @description:
 */
public class QualityBean implements Serializable {
    private static final long serialVersionUID = 7774352248569445319L;
    /**
     * 0error 1deep 2shallow 3dream
     */
    private int type;
    private float grade;

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
