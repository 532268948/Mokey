package com.example.module_report.bean;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 13:52
 * @description:
 */
public class QualityBean {
    /**
     * 0error 1deep 2shallow 3dream
     */
    private Integer type;
    private float grade;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
