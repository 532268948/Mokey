package com.example.lib_common.music;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/23
 * @time : 21:13
 * @email : 15869107730@163.com
 * @note : 音频来源，控制下载Music
 */
public enum MusicSource {
    NetWork(1,"NetWork"),
    //表示默认播放不需要网络请求
    None(2, "None");

    public int code;
    public String name;

    MusicSource(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
