package com.example.lib_common.bean.response;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 13:49
 * @email : 15869107730@163.com
 * @note :
 */
public class EntranceIconsBean {
    private int id;
    private String name;
    private EntranceIconBean entrance_icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntranceIconBean getEntrance_icon() {
        return entrance_icon;
    }

    public void setEntrance_icon(EntranceIconBean entrance_icon) {
        this.entrance_icon = entrance_icon;
    }
}
