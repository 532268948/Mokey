package com.example.lib_common.bean;

import android.media.Ringtone;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/24
 * @time : 13:14
 * @email : 15869107730@163.com
 * @note :
 */
public class RingtoneBean {
    private Ringtone ringtone;
    private String path;

    public RingtoneBean(Ringtone ringtone, String path) {
        this.ringtone = ringtone;
        this.path = path;
    }

    public Ringtone getRingtone() {
        return ringtone;
    }

    public void setRingtone(Ringtone ringtone) {
        this.ringtone = ringtone;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
