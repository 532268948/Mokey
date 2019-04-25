package com.example.lib_common.bean;

import com.example.lib_common.bean.response.MusicBean;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.entity.MusicBefore;
import com.example.lib_common.music.MusicSource;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/16
 * @time : 22:23
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicItem extends BaseItem {


    /**
     * 播放次数
     */
    private long playTimes;
    /**
     * 音频时长
     */
    private String duration;
    /**
     * 播放音频的id
     */
    private long musicId;
    /**
     * 音频名字
     */
    private String name;
    /**
     * 作者
     */
    private String author;

    /**
     * 封面图片
     */
    private String cover;

    /**
     * 播放音频的url
     */
    private String url;
    /**
     * 边下载边播放下载的路径
     */
    private String cachedFile;
    /**
     * 本地下载文件路径
     */
    private String localFile;
    /**
     * music来源
     */
    private MusicSource source = MusicSource.NetWork;
    /**
     * 是否正在播放
     */
    private boolean isPlaying;

    /**
     * 是否边下载边播放
     */
    private boolean downloadWhenPlaying = true;

    /**
     * 是否需要付款播放
     */
    private boolean isNeedPay = false;

    /**
     * 是否已经付款
     */
    private boolean isHasPay = false;

    /**
     * 价格
     */
    private String price;

    /**
     * 歌曲状态 0展示 1不展示
     */
    private int status = 0;

    public MusicItem() {
    }

    public MusicItem(MusicBean musicBean) {
        if (musicBean != null) {
            if (musicBean.getType() != null && musicBean.getType() == Constant.ServerItemType.MUSIC_BEFORE) {
                itemType = Constant.ItemType.MUSIC_BEFORE;
                musicId = musicBean.getId() == null ? -1 : musicBean.getId();
                name = musicBean.getName() == null ? "" : musicBean.getName();
                author = musicBean.getAuthor() == null ? "" : musicBean.getAuthor();
                cover = musicBean.getCover() == null ? "" : musicBean.getCover();
                duration = musicBean.getDuring() == null ? "00:00" : musicBean.getDuring();
                if (musicBean.getFree() == null) {
                    isNeedPay = false;
                } else {
                    if (musicBean.getFree() == 0) {
                        isNeedPay = false;
                    } else if (musicBean.getFree() == 1) {
                        isNeedPay = true;
                    }
                }
                price = musicBean.getPrice() == null ? "0" : musicBean.getPrice().stripTrailingZeros().toPlainString();
                url = musicBean.getResource() == null ? "" : Constant.BASE_URL + musicBean.getResource();
                playTimes = musicBean.getPlayTimes() == null ? 0 : musicBean.getPlayTimes();
                status = musicBean.getStatus() == null ? 0 : musicBean.getStatus();
            }
        }
    }

    public MusicItem(MusicBefore musicBefore) {
        if (musicBefore != null) {
            itemType = Constant.ItemType.MUSIC_BEFORE;
            musicId = musicBefore.getId() == null ? -1 : musicBefore.getId();
            playTimes = musicBefore.getPlayTimes() == null ? 0 : musicBefore.getPlayTimes();
            duration = musicBefore.getDuring() == null ? "00:00" : musicBefore.getDuring();
            name = musicBefore.getName() == null ? "" : musicBefore.getName();
            author = musicBefore.getAuthor() == null ? "" : musicBefore.getAuthor();
            cover = musicBefore.getCover() == null ? "" : musicBefore.getCover();
            url = musicBefore.getResource() == null ? "" : musicBefore.getResource();
            localFile = musicBefore.getLocalFile() == null ? "" : musicBefore.getLocalFile();
            price = String.valueOf(musicBefore.getPrice());
            if (musicBefore.getFree() == 0) {
                isNeedPay = false;
            } else {
                isNeedPay = true;
            }
            status = musicBefore.getStatus();
        }
    }

    public MusicItem(MusicOnlineItem musicOnlineItem) {
        playTimes = musicOnlineItem.getPlayTimes();
        duration = musicOnlineItem.getDuration();
        musicId = musicOnlineItem.getMusicId();
        name = musicOnlineItem.getName();
        author = musicOnlineItem.getAuthor();
        cover = musicOnlineItem.getCover();
        url = musicOnlineItem.getUrl();
        cachedFile = musicOnlineItem.getCachedFile();
        localFile = musicOnlineItem.getLocalFile();
        source = musicOnlineItem.getSource();
        isPlaying = musicOnlineItem.isPlaying();
        downloadWhenPlaying = musicOnlineItem.isDownloadWhenPlaying();
        isNeedPay = musicOnlineItem.isNeedPay();
        isHasPay = musicOnlineItem.isHasPay();
        status = musicOnlineItem.getStatus();
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(long playTimes) {
        this.playTimes = playTimes;
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCachedFile() {
        return cachedFile;
    }

    public void setCachedFile(String cachedFile) {
        this.cachedFile = cachedFile;
    }

    public String getLocalFile() {
        return localFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public MusicSource getSource() {
        return source;
    }

    public void setSource(MusicSource source) {
        this.source = source;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isDownloadWhenPlaying() {
        return downloadWhenPlaying;
    }

    public void setDownloadWhenPlaying(boolean downloadWhenPlaying) {
        this.downloadWhenPlaying = downloadWhenPlaying;
    }

    public boolean isNeedPay() {
        return isNeedPay;
    }

    public void setNeedPay(boolean needPay) {
        isNeedPay = needPay;
    }

    public boolean isHasPay() {
        return isHasPay;
    }

    public void setHasPay(boolean hasPay) {
        isHasPay = hasPay;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
