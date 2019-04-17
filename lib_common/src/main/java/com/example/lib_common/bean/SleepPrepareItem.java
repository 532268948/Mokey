package com.example.lib_common.bean;

/**
 * @author: tianhuaye
 * @date: 2019/1/29 13:44
 * @description:
 */
public class SleepPrepareItem extends BaseItem {
    private long id;
    private int imageResId;
    private int bigImageResId;
    private String title;
    private String shortTip;
    private String message;
    private boolean checked;
    private int checkNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getBigImageResId() {
        return bigImageResId;
    }

    public void setBigImageResId(int bigImageResId) {
        this.bigImageResId = bigImageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTip() {
        return shortTip;
    }

    public void setShortTip(String shortTip) {
        this.shortTip = shortTip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }
}
