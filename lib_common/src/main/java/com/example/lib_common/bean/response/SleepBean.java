package com.example.lib_common.bean.response;

import java.io.Serializable;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/2
 * @time : 14:57
 * @email : 15869107730@163.com
 * @note :
 */
public class SleepBean implements Serializable {
    private static final long serialVersionUID = -144302231938931041L;
    private Integer id;
    private Integer grade;
    private Long start_time;
    private Long end_time;
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
