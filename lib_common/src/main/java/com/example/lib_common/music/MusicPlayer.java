package com.example.lib_common.music;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.lib_common.R;
import com.example.lib_common.bean.MusicItem;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.ActivityManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/23
 * @time : 15:23
 * @email : 15869107730@163.com
 * @note : musicService的管理类
 */
public class MusicPlayer implements OnMusicPlayerCallBack {

    /**
     * 当前播放音乐指针
     */
    private int currentIndex;

    /**
     * 当前播放的音乐id
     */
    private long currentMusicId;
    /**
     * 上次播放音频id
     */
    private long lastMusicId = 0L;

    /**
     * 是否正在定位到指定时间
     */
    private boolean seeking = false;

    /**
     * 当前播放的item
     */
    private MusicItem curItem;

    private Context mContext;

    private MusicState mState = MusicState.Stopped;

    private MusicSource source = MusicSource.None;

    /**
     * 播放模式
     */
    private PlayMode playMode = PlayMode.order;

    /**
     * 通知管理
     */
    private NotificationManager notificationMgr;
    private Notification notification = null;
    private RemoteViews smallView;

    private ServiceConnection connection;

    private MusicService service;

    /**
     * 音乐集合
     */
    private List<MusicItem> playList;
    /**
     * Music状态监听集合
     */
    private List<OnMusicPlayStateListener> listeners;

    private ServiceConnectionListener serviceConnectionListener;

    /**
     * 播放进度监听器
     */
    private OnPlayProgressUpdateListener onPlayProgressUpdateListener;

    private CacheableMediaPlayer.MusicControlInterface musicCacheSuccessListener;

    /**
     * 是否监听歌曲播放进度
     */
    private boolean checking = true;

    MusicPlayer(Context context) {
        this.mContext = context.getApplicationContext();
        notificationMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        bindService();
    }

    @Override
    public void onStateChanged(MusicState state) {
        mState = state;
        Log.e("MusicPlayer", "onStateChanged: " + currentMusicId + " " + state);
        if (mState == MusicState.Stopped) {
            if (listeners != null) {
                for (int i = 0; i < listeners.size(); i++) {
                    OnMusicPlayStateListener l = listeners.get(i);
                    if (l != null) {
                        l.onStopped(curItem);
                    }
                }
            }
        } else if (mState == MusicState.Preparing) {
            if (listeners != null) {
                for (int i = 0; i < listeners.size(); i++) {
                    OnMusicPlayStateListener l = listeners.get(i);
                    if (l != null) {
                        l.onPrepare(curItem);
                    }
                }
            }
        } else if (mState == MusicState.Playing) {
            if (listeners != null) {
                for (int i = 0; i < listeners.size(); i++) {
                    OnMusicPlayStateListener l = listeners.get(i);
                    if (l != null) {
                        l.onPlay(getCurMusicItem());
                    }
                }
            }
        } else if (mState == MusicState.Paused) {
            if (listeners != null) {
                for (int i = 0; i < listeners.size(); i++) {
                    OnMusicPlayStateListener l = listeners.get(i);
                    if (l != null) {
                        l.onPaused(curItem);
                    }
                }
            }
        } else if (mState == MusicState.Completed) {
            if (listeners != null) {
                for (int i = 0; i < listeners.size(); i++) {
                    OnMusicPlayStateListener l = listeners.get(i);
                    if (l != null) {
                        l.onComplete(curItem);
                    }
                    if (service != null) {
                        next(true);
                    }
                }
            }
        }
    }

    @Override
    public void onControlAction(String action) {
        updateNotification();
    }


    @Override
    public void onSeekToLast() {

    }

    @Override
    public void onSeekCompleted() {

    }

    public void setMusicCacheProgressListener(CacheableMediaPlayer.OnCachedProgressUpdateListener onCachedProgressUpdateListener) {
        if (service != null) {
            service.setMusicCacheProgressListener(onCachedProgressUpdateListener);
        }
    }

    public void setMusicCacheSuccessListener(CacheableMediaPlayer.MusicControlInterface musicCacheSuccessListener) {
        this.musicCacheSuccessListener = musicCacheSuccessListener;
        if (service != null) {
            service.setMusicCacheSuccessListener(this.musicCacheSuccessListener);
        }
    }

    private Runnable progressListener = new Runnable() {
        @Override
        public void run() {
            if (checking && onPlayProgressUpdateListener != null) {
                onPlayProgressUpdateListener.onPlayProgress(getCurrentPosition());
            }
        }
    };

    public interface OnPlayProgressUpdateListener {
        void onPlayProgress(int position);
    }


    /**
     * 重新绑定MusicService
     */
    void reBindForLowMemoryKilled() {
        if (service == null) {
            bindService();
        }
    }

    /**
     * 注册Music状态监听器
     *
     * @param l
     */
    void registerCallback(OnMusicPlayStateListener l) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    /**
     * 注销Music状态监听器
     *
     * @param l
     */
    void unregisterCallback(OnMusicPlayStateListener l) {
        if (listeners != null) {
            listeners.remove(l);
        }
    }

    void play(long musicId) {
        //与上次的音频id相同
        if (lastMusicId == musicId) {
            //正在播放
            if (mState == MusicState.Playing) {
                //暂停播放
                pause();
            } else if (mState == MusicState.Paused) {
                //恢复播放
                recover();
            } else {
                //开始播放
                stop(false);
                play();
            }
        } else {//不同
            //未播放
            if (mState == MusicState.Stopped) {
                changeIndex(musicId);
                initMusicItem(currentIndex);
                play();
            } else {
                changeIndex(musicId);
                initMusicItem(currentIndex);
                stop(false);
                play();
            }
        }
    }

    void play() {
//        notifyLimitCount();
//        notifyLimitTime();
        tryToGetAudioFocus();
        if (mState == MusicState.Stopped) {
            // If we're stopped, just go ahead to the next song and start
            // playing
            startPlay();
        } else if (mState == MusicState.Paused) {
            recover();
        }
        lastMusicId = currentMusicId;
//        sendLimitTimeMsg();

//        mPlayed = true;
    }

    /**
     * 上一首
     */
    void prev() {
//        updateProgress();
        //若第一首点击上一首则无响应
        if (ShouldStop(false)) {
            return;
        }
        if (source == MusicSource.None) {
            changeAndPlay(false, false);
        } else {
            //是否需要付费
            if (!CheckIsNeedPay(false)) {
//                notifyBBInterceptors();
                //此处设置支付通知
                return;
            }
            //来源为网络
            if (source == MusicSource.NetWork) {
                boolean outOfIndex = ShouldStop(false);
                if (!outOfIndex) {
                    changeAndPlay(false, false);
                    return;
                }
            } else {//来源为本地
                changeIndex(false, false);
                initMusicItem(currentIndex);
            }
//            initBBController();
//            bbController.controlApi(bbApiRequest(false));
        }
    }

    /**
     * 播放下一首
     *
     * @param completed 当前歌曲是否已播放完
     */
    void next(boolean completed) {
//        updateProgress();
        //播放完
        if (completed) {
            if (ShouldStop(true)) {
                //是否最后一首播完，播完则pause并seekto 0
                pause();
                seek(0);
                notifyPosition(0);
                return;
            }
        } else { //未播放完
            if (ShouldStop(true)) {
                //若最后一首点击下一首则无响应
                return;
            }
        }

        if (source == MusicSource.None) {
            changeAndPlay(true, completed);
        } else {
            if (!CheckIsNeedPay(true)) {
//                notifyBBInterceptors();
                if (completed) {
//                    notifyBBInterceptors();
                    pause();
                    seek(0);
                    notifyPosition(0);
                }
                return;
            }
            if (source == MusicSource.NetWork) {
                boolean outOfIndex = ShouldStop(true);
                if (!outOfIndex) {
                    changeAndPlay(true, completed);
                    return;
                }
            } else {
                changeIndex(true, completed);
                initMusicItem(currentIndex);
            }
//            initBBController();
//            bbController.controlApi(bbApiRequest(true));
        }
    }

    /**
     * 暂停播放
     */
    void pause() {
        if (service != null) {
            service.pause(false);
        }
    }

    /**
     * 停止播放
     *
     * @param updateNotify 是否通知
     */
    void stop(boolean updateNotify) {
        if (mState != MusicState.Stopped) {
            if (service != null) {
                service.stop(updateNotify);
            }
        }
    }

    /**
     * 跳到指定时间
     *
     * @param time
     */
    void seekTo(int time) {
        seek(time);
    }

//    /**
//     * 更新lastMusicId 、lastSource
//     */
//    void updateLastIds() {
//        lastMusicId = currentMusicId;
//        lastSource = source;
//    }

    void initMusicItemList(List<MusicItem> items, long musicId, boolean play) {
        if (items == null || items.isEmpty()) {
            return;
        }
        if (playList != null) {
            if (playList.equals(items)) {

            } else {
                playList = items;
            }
        } else {
            playList = items;
        }

        MusicItem musicItem = getMusicItem(items, musicId);
        curItem = musicItem;
        currentMusicId = musicId;
        if (musicItem == null) {
            return;
        }
        mState = MusicState.Stopped;
        if (play) {
            play();
        }
    }

    private void updateNotification() {
        notification = getNotification();
        goNotify();
    }

    private Notification getNotification() {
        Notification status = null;
        if (curItem != null) {
            int resId = R.drawable.drawable_album_default_thumb;
            status = new Notification(resId, null, System.currentTimeMillis());
            if (smallView == null) {
                smallView = new RemoteViews(mContext.getPackageName(), R.layout.music_notify);
            }
            Glide.with(mContext).load(curItem.getCover()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    smallView.setImageViewBitmap(R.id.iv_status_bar_small_thumb, resource);
                }
            });
            smallView.setTextViewText(R.id.tv_status_bar_music_title, curItem.getName());
            if (mState == MusicState.Playing) {
                smallView.setOnClickPendingIntent(R.id.iv_status_bar_small_playback,
                        controlMusicIntent(ControlAction.ACTION_PAUSE));
                smallView.setImageViewResource(R.id.iv_status_bar_small_playback,
                        R.drawable.btn_treasury_music_noti_pause);
            }

            if (mState == MusicState.Paused || mState == MusicState.Stopped) {
                smallView.setOnClickPendingIntent(R.id.iv_status_bar_small_playback,
                        controlMusicIntent(ControlAction.ACTION_PLAY));
                smallView.setImageViewResource(R.id.iv_status_bar_small_playback,
                        R.drawable.btn_treasury_music_noti_play);
            }

            smallView.setOnClickPendingIntent(R.id.iv_status_bar_small_next, controlMusicIntent(ControlAction
                    .ACTION_NEXT));

            smallView.setOnClickPendingIntent(R.id.iv_status_bar_small_del, controlMusicIntent(ControlAction
                    .ACTION_STOP));
            status.contentView = smallView;
            status.contentIntent = getPendingIntent();
            status.flags |= Notification.FLAG_ONGOING_EVENT;
            status.flags |= Notification.FLAG_FOREGROUND_SERVICE;
            status.when = -1000;
        }
        return status;
    }

    private PendingIntent getPendingIntent() {
        Intent it = new Intent();
        String className = null;
        Activity topActivity = ActivityManager.getInstance().getTopActivity();
        if (topActivity != null) {
            className = topActivity.getComponentName().getClassName();
        }
        if (!TextUtils.isEmpty(className)) {
            try {
                it.setComponent(new ComponentName(mContext, Class.forName(className)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // it.setAction(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_LAUNCHER);
        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                R.string.app_name, it, PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

    private PendingIntent controlMusicIntent(String action) {
        if (service != null) {
            return service.getControlMusicIntent(action);
        }
        return null;
    }

    private void goNotify() {
        if (notification != null) {
            if (notificationMgr == null) {
                notificationMgr = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            if (notificationMgr != null) {
                try {
                    notificationMgr.notify(Constant.NOTIFICATION_MUSIC_ID, notification);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从暂停恢复播放
     */
    private void recover() {
        if (service != null) {
            service.recover();
        }
    }

    /**
     * 检查音频是否允许播放
     *
     * @param next true下一首  false上一首
     * @return
     */
    private boolean CheckIsNeedPay(boolean next) {
        MusicItem item = getNextOrPrevMusicItem(next);
        return item != null && (!item.isNeedPay() || (item.isNeedPay() && item.isHasPay()));
    }

    /**
     * 获取下一首或前一首歌曲
     *
     * @param next true下一首  false上一首
     * @return
     */
    private MusicItem getNextOrPrevMusicItem(boolean next) {
        int index = currentIndex;
        if (next) {
            index++;
            if (playList == null || index >= playList.size()) {
                index = 0;
            }
        } else {
            index--;
            if (index < 0) {
                if (playList != null && !playList.isEmpty()) {
                    index = playList.size() - 1;
                } else {
                    index = 0;
                }
            }
        }
        return getMusicItem(index);
    }

    /**
     * 获取指定位置的MusicItem
     *
     * @param index 位置
     * @return
     */
    private MusicItem getMusicItem(int index) {
        if (playList != null && index >= 0 && index < playList.size()) {
            return playList.get(index);
        }
        return null;
    }

    /**
     * 从集合中获取指定id的音频
     *
     * @param items   音频集合
     * @param musicId 音频id
     * @return
     */
    private MusicItem getMusicItem(List<MusicItem> items, long musicId) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        for (MusicItem musicItem : items) {
            if (musicItem == null) {
                continue;
            }
            if (musicItem.getMusicId() == musicId) {
                return musicItem;
            }
        }
        return items.get(0);
    }

    /**
     * 开始播放
     */
    private void startPlay() {
        MusicItem item = getCurMusicItem();

        String filename = null;
        if (item != null) {
            if (!TextUtils.isEmpty(item.getLocalFile())) {
                File f = new File(item.getLocalFile());
                if (f.exists()) {
                    filename = item.getLocalFile();
                }
            }

            if (TextUtils.isEmpty(filename)) {
                if (!TextUtils.isEmpty(item.getCachedFile())) {
                    File f = new File(item.getCachedFile());
                    if (f.exists()) {
                        filename = item.getCachedFile();
                    }
                }
            }
            if (TextUtils.isEmpty(filename) && TextUtils.isEmpty(item.getUrl())) {
                return;
            }
            startPlay(item.getMusicId(), filename, item.getUrl(), item.getCachedFile(), item.isDownloadWhenPlaying());
        }
    }

    private void startPlay(long id, String filename, String url, String cachedFile, boolean downloadWhenPlaying) {
        if (service != null) {
            service.startPlay(id, filename, url, cachedFile, downloadWhenPlaying);
        }
    }

    /**
     * 获取当前MusicItem
     *
     * @return
     */
    public MusicItem getCurMusicItem() {
        if (curItem != null) {
            return curItem;
        }
        if (playList != null && currentIndex >= 0 && currentIndex < playList.size()) {
            return playList.get(currentIndex);
        }
        return null;
    }

    /**
     * 获取音频焦点
     */
    private void tryToGetAudioFocus() {
        if (service != null) {
            service.tryToGetAudioFocus();
        }
    }

    /**
     * 更新播放
     *
     * @param next
     * @param completed
     */
    private void changeAndPlay(boolean next, boolean completed) {
        changeIndex(next, completed);
        initMusicItem(currentIndex);
        stop(false);
        play();
    }

    /**
     * 更新currentIndex为指定musicId的集合下标
     *
     * @param musicId MusicItem的musicId
     */
    private void changeIndex(long musicId) {
        if (playList != null && !playList.isEmpty()) {
            MusicItem item;
            for (int i = 0; i < playList.size(); i++) {
                item = playList.get(i);
                if (item != null && item.getMusicId() == musicId) {
                    currentIndex = i;
                    break;
                }
            }
        }
    }

    /**
     * 更新currentIndex
     *
     * @param next        true下一首 false前一首
     * @param isCompleted 是否播放完
     */
    private void changeIndex(boolean next, boolean isCompleted) {
        if (playMode == PlayMode.singleCycle && isCompleted) {
            //如果是播完当前歌曲，且模式是单曲，不让切下一首
        } else {
            if (next) {
                currentIndex++;
                if (playList == null || currentIndex >= playList.size()) {
                    currentIndex = 0;
                }
            } else {
                currentIndex--;
                if (currentIndex < 0) {
                    if (playList != null && !playList.isEmpty()) {
                        currentIndex = playList.size() - 1;
                    } else {
                        currentIndex = 0;
                    }
                }
            }
        }
    }

    /**
     * 更新当前音乐MusicItem
     *
     * @param index
     */
    private void initMusicItem(int index) {
        if (playList != null && index >= 0 && index < playList.size()) {
            MusicItem item = playList.get(index);
            currentMusicId = item.getMusicId();
            curItem = item;
            lastMusicId = currentMusicId;
        }
    }

    /**
     * @param next
     * @return 检查是否播放到底或者最前面
     */
    private boolean ShouldStop(boolean next) {
        int index = currentIndex;
        if (next) {
            index++;
        } else {
            index--;
        }
        if (index < 0 || (playList != null && index >= playList.size())) {
            return true;
        }
        return false;
    }

    /**
     * 更新当前时间
     *
     * @param pos
     */
    private void notifyPosition(int pos) {
        if (listeners != null) {
            for (int i = 0; i < listeners.size(); i++) {
                OnMusicPlayStateListener l = listeners.get(i);
                if (l != null) {
                    l.onPosition(curItem, pos);
                }
            }
        }
    }

    private void seek(int time) {
        if (!seeking) {
            if (service != null && time >= 0) {
                seeking = true;
                service.seekTo(time);
            }
        }
    }

    /**
     * 获取音乐时长
     *
     * @return
     */
    int getDuration() {
        if (service != null && mState != MusicState.Stopped && mState != MusicState.Preparing) {
            return service.getDuration();
        }
        return 0;
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    private boolean isPlaying() {
        return service != null && service.isPlaying();
    }

    /**
     * 获取当前播放时间
     *
     * @return
     */
    private int getCurrentPosition() {
        if (service != null) {
            return service.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 绑定MusicService
     */
    private void bindService() {
        if (connection == null) {
            connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder iBinder) {
                    service = ((MusicService.MusicBinder) iBinder).getService();
                    service.setCallback(MusicPlayer.this);
                    if (serviceConnectionListener != null) {
                        serviceConnectionListener.serviceConnected();
                    }
                    service.setMusicCacheSuccessListener(musicCacheSuccessListener);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    if (service != null) {
                        service.setCallback(null);
                        service.setMusicCacheProgressListener(null);
                        service.setMusicCacheSuccessListener(null);
                        service = null;
                    }
                    if (serviceConnectionListener != null) {
                        serviceConnectionListener.serviceDisconnected();
                    }
                }
            };
        }
        Intent intent = new Intent(mContext, MusicService.class);
        mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    void setConnectListener(ServiceConnectionListener serviceConnectionListener) {
        this.serviceConnectionListener = serviceConnectionListener;
    }

    interface ServiceConnectionListener {

        void serviceConnected();

        void serviceDisconnected();
    }
}
