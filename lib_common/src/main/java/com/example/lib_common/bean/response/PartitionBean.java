package com.example.lib_common.bean.response;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 13:51
 * @email : 15869107730@163.com
 * @note :
 */
public class PartitionBean {
    private int id;
    private String name;
    private String area;
    private SubIconBean sub_icon;
    private int count;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public SubIconBean getSub_icon() {
        return sub_icon;
    }

    public void setSub_icon(SubIconBean sub_icon) {
        this.sub_icon = sub_icon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
