package com.example.lib_common.bean;

/**
 * @author: tianhuaye
 * @date: 2019/1/7 16:05
 * @description:
 */
public class DreamBean extends BaseItem {
    private long music_id;
    private long dream_time;
    private long during;
    private String path;
    private boolean isPlaying=false;

    public long getMusic_id() {
        return music_id;
    }

    public void setMusic_id(long music_id) {
        this.music_id = music_id;
    }

    public long getDream_time() {
        return dream_time;
    }

    public void setDream_time(long dream_time) {
        this.dream_time = dream_time;
    }

    public long getDuring() {
        return during;
    }

    public void setDuring(long during) {
        this.during = during;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    @Override
    public String toString() {
        return "DreamBean{" +
                "music_id=" + music_id +
                ", dream_time=" + dream_time +
                ", during=" + during +
                ", path='" + path + '\'' +
                ", isPlaying=" + isPlaying +
                ", itemType=" + itemType +
                '}';
    }
}
