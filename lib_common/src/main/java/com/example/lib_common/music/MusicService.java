package com.example.lib_common.music;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.lib_common.base.CommonUI;

import java.io.IOException;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/22
 * @time : 22:20
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnSeekCompleteListener,
        MusicFocusable {
    /**
     * The volume we set the media player to when we lose audio focus, but are
     * allowed to reduce
     * the volume instead of stopping playback.
     */
    public static final float DUCK_VOLUME = 0.1f;
    /**
     * Our instance of our MusicRetriever, which handles scanning for media and
     * providing titles and URIs as we need.
     * MusicRetriever mRetriever;
     * our RemoteControlClient object, which will use remote control APIs
     * available in
     * SDK level >= 14, if they're available.
     * RemoteControlClientCompat mRemoteControlClientCompat;
     */
    ComponentName mMediaButtonReceiverComponent;
    /**
     * our AudioFocusHelper object, if it's available (it's available on SDK
     * level >= 8)
     * If not available, this will be null. Always check for null before using!
     */
    AudioFocusHelper mAudioFocusHelper = null;
    /**
     * MusicPlayer回调接口
     */
    private OnMusicPlayerCallBack callback;
    /**
     * 本地文件路径
     */
    private String mLocalFile;
    /**
     * url
     */
    private String mUrl;
    /**
     * 缓存文件
     */
    private String mCacheFile;

    /**
     * 音频id
     */
    public long musicId;

    /**
     * 是否是本地
     */
    private boolean isLocal;

    /**
     * 当前音乐播放文件是否通过网络获取
     */
    private boolean mIsStreaming = false;

    /**
     * 网络环境判断
     */
    private WifiManager.WifiLock mWifiLock;

    /**
     * 音频管理器
     */
    private AudioManager mAudioManager;

    /**
     * 音频播放器
     */
    private CacheableMediaPlayer mPlayer;

    /**
     * 停止播放广播
     */
    private StopReceiver mStopReceiver;

    private boolean mPausedOnFocusLost = false;

    private MusicBinder binder = new MusicBinder();

    private MusicState mState = MusicState.Stopped;

    private boolean mIsForeground;

    /**
     * why did we pause? (only relevant if mState == State.Paused)
     * PauseReason mPauseReason = PauseReason.UserRequest;
     * do we have audio focus?
     */
    private enum AudioFocus {
        /**
         * we don't have audio focus, and can't duck
         */
        NoFocusNoDuck,
        /**
         * we don't have focus, but can play at a low volume
         */
        NoFocusCanDuck,
        /**
         * ("ducking")
         * we have full audio focus
         */
        Focused
    }

    AudioFocus mAudioFocus = AudioFocus.NoFocusNoDuck;

    @Override
    public void onCreate() {
        // Create the Wifi lock (this does not acquire the lock, this just
        // creates it)
        try {
            mWifiLock = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE))
                    .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");

            mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

            // Create the retriever and start an asynchronous task that will
            // prepare
            // it.
            // mRetriever = new MusicRetriever(getContentResolver());
            // (new PrepareMusicRetrieverTask(mRetriever,this)).execute();

            // create the Audio Focus Helper, if the Audio Focus feature is
            // available (SDK 8 or above)
            mAudioFocusHelper = new AudioFocusHelper(getApplicationContext(), this);
            // always
            // "have" audio focus
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMediaButtonReceiverComponent = new ComponentName(this, MusicIntentReceiver.class);

        registerStopReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            Log.e("MusicService", "onStartCommand: " + action);
            if (!TextUtils.isEmpty(action)) {
                switch (action) {
                    case ControlAction.ACTION_TOGGLE_PLAYBACK:
                        processTogglePlaybackRequest();
                        break;
                    case ControlAction.ACTION_PLAY:
                        play();
                        break;
                    case ControlAction.ACTION_PAUSE:
                        pause(true);
                        break;
                    case ControlAction.ACTION_NEXT:
                        next();
                        break;
                    case ControlAction.ACTION_STOP:
                        stop(true);
                        break;
                    case ControlAction.ACTION_PREV:
                        prev();
                        break;
                }
            }
        }
        return START_NOT_STICKY; // Means we started the service, but don't want
        // it to
        // restart in case it's killed.
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // The media player finished playing the current song, so we go ahead
        // and start the next.
        notifyMusicState(MusicState.Completed);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == CacheableMediaPlayer.MEDIA_ERROR_GET_SIZE
                || what == CacheableMediaPlayer.STOP_DOWNLOAD_WHILE_PLAY) {
            startPlay(musicId, mLocalFile, mUrl, mCacheFile, false);
        } else {
            mState = MusicState.Stopped;
            notifyMusicState(mState);
//            notifyLogicAction(LogicAction.report_to_server);
            relaxResources(true, true);
            giveUpAudioFocus();
        }
        // true indicates we handled the error
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // The media player is done preparing. That means we can start playing!
        mState = MusicState.Playing;
        notifyMusicState(mState);
        configAndStartMediaPlayer(true);
        readyToSeek();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        if (callback != null) {
            callback.onSeekCompleted();
        }
    }

    /**
     * 获取音频焦点时调用
     */
    @Override
    public void onGainedAudioFocus() {
        mAudioFocus = AudioFocus.Focused;

        // restart media player with new focus settings
        if (mState == MusicState.Paused && mPausedOnFocusLost) {
            play();
            mPausedOnFocusLost = false;
        }
        // configAndStartMediaPlayer();
    }

    /**
     * 失去音频焦点时调用
     *
     * @param canDuck If true, audio can continue in "ducked" mode (low volume). Otherwise, all
     */
    @Override
    public void onLostAudioFocus(boolean canDuck) {
        mAudioFocus = canDuck ? AudioFocus.NoFocusCanDuck : AudioFocus.NoFocusNoDuck;

        // start/restart/pause media player with new focus settings
        // if (mPlayer != null && mPlayer.isPlaying())
        // configAndStartMediaPlayer();
        if (mState == MusicState.Playing) {
            pause(mIsForeground);
            mPausedOnFocusLost = true;
        }
    }

    public PendingIntent getControlMusicIntent(String action) {
        if (TextUtils.isEmpty(action)) {
            return null;
        }

        Intent intent = new Intent(action);
        intent.setComponent(mMediaButtonReceiverComponent);
        intent.setPackage(getPackageName());
        PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return contentIntent;
    }


    public void setIsForeground(boolean mIsForeground) {
        this.mIsForeground = mIsForeground;
    }

    /**
     * 添加回调接口
     *
     * @param callback
     */
    public void setCallback(OnMusicPlayerCallBack callback) {
        this.callback = callback;
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        return mPlayer != null && mPlayer.isPlaying();
    }

    /**
     * 获取当前播放时间
     *
     * @return
     */
    public int getCurrentPosition() {
        if (mPlayer != null) {
            return mPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 获取音乐时长
     *
     * @return
     */
    public int getDuration() {
        if (mPlayer != null) {
            return mPlayer.getDuration();
        }
        return 0;
    }

    public void setMusicCacheProgressListener(CacheableMediaPlayer.OnCachedProgressUpdateListener onCachedProgressUpdateListener) {
        if (mPlayer != null) {
            mPlayer.setMusicCacheProgressListener(onCachedProgressUpdateListener);
        }
    }

    public void setMusicCacheSuccessListener(CacheableMediaPlayer.MusicControlInterface musicCacheSuccessListener) {
        if (mPlayer != null) {
            mPlayer.setMusicCacheSuccessListener(musicCacheSuccessListener);
        }
    }

    void tryToGetAudioFocus() {
        if (mAudioFocus != AudioFocus.Focused && mAudioFocusHelper != null && mAudioFocusHelper.requestFocus()) {
            mAudioFocus = AudioFocus.Focused;
        }
    }

    /**
     * Releases resources used by the service for playback. This includes the
     * "foreground service" status and notification, the wake locks and possibly
     * the MediaPlayer.
     *
     * @param releaseMediaPlayer Indicates whether the Media Player should also be released or
     *                           not
     */
    void relaxResources(boolean releaseMediaPlayer, boolean stopForeground) {
        // stop being a foreground service
        if (stopForeground) {
            stopForeground(true);
        }
        // stop and release the Media Player, if it's available
        if (releaseMediaPlayer && mPlayer != null) {
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }

        // we can also release the Wifi lock, if we're holding it
        if (mWifiLock.isHeld()) {
            mWifiLock.release();
        }
    }

    /**
     * Makes sure the media player exists and has been reset. This will create
     * the media player if needed, or reset the existing media player if one
     * already exists.
     */
    void createMediaPlayerIfNeeded() {
        if (mPlayer == null) {
            mPlayer = new CacheableMediaPlayer();

            // Make sure the media player will acquire a wake-lock while
            // playing. If we don't do
            // that, the CPU might go to sleep while the song is playing,
            // causing playback to stop.
            //
            // Remember that to use this, we have to declare the
            // android.permission.WAKE_LOCK
            // permission in AndroidManifest.xml.
            mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

            // we want the media player to notify us when it's ready preparing,
            // and when it's done
            // playing:
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnSeekCompleteListener(this);
        } else {
            mPlayer.stop();
            mPlayer.reset();
        }
    }

    /**
     * 从暂停恢复播放
     */
    void recover() {
        // If we're paused, just continue playback and restore the
        // 'foreground service' state.
        mState = MusicState.Playing;
        notifyMusicState(mState);
        // setUpAsForeground();
        configAndStartMediaPlayer(false);
    }

    /**
     * Reconfigures MediaPlayer according to audio focus settings and
     * starts/restarts it. This method starts/restarts the MediaPlayer
     * respecting the current audio focus state. So if we have focus, it will
     * play normally; if we don't have focus, it will either leave the
     * MediaPlayer paused or set it to a low volume, depending on what is
     * allowed by the current focus settings. This method assumes mPlayer !=
     * null, so if you are calling it, you have to do so from a context where
     * you are sure this is the case.
     */
    void configAndStartMediaPlayer(boolean update) {
        if (mPlayer == null) {
            return;
        }
        if (mAudioFocus == AudioFocus.NoFocusNoDuck) {
            // If we don't have audio focus and can't duck, we have to pause,
            // even if mState
            // is State.Playing. But we stay in the Playing state so that we
            // know we have to resume
            // playback once we get the focus back.
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
            return;
        } else if (mAudioFocus == AudioFocus.NoFocusCanDuck) {
            // we'll be relatively
            mPlayer.setVolume(DUCK_VOLUME, DUCK_VOLUME);
        } else {
            // we can be loud
            mPlayer.setVolume(1.0f, 1.0f);
        }

        if (!mPlayer.isPlaying()) {
            mPlayer.start();
//            if (mIsForeground) {
//                notifyLogicAction(LogicAction.notification_update);
//            }
//            if (update) {
//                notifyLogicAction(LogicAction.play_num_update);
//            }
        }
    }

    /**
     * 音乐跳转到指定时间
     *
     * @param time
     */
    void seekTo(int time) {
        if (mPlayer != null) {
            mPlayer.seekTo(time);
        }
    }

    /**
     * Starts playing the next song. If manualUrl is null, the next song will be
     * randomly selected from our Media Retriever (that is, it will be a random
     * song in the user's device). If manualUrl is non-null, then it specifies
     * the URL or path to the song that will be played next.
     */
    void startPlay(long id, String filename, String url, String cachedFile, boolean downloadWhenPlaying) {
        mState = MusicState.Stopped;
        notifyMusicState(mState);
        // release everything except MediaPlayer
        relaxResources(false, false);

        try {
            if (filename != null) {
                //playing a locally available song

                mIsStreaming = false;
                // set the source of the media player a a content URI
                createMediaPlayerIfNeeded();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setDataSource(getApplicationContext(), Uri.parse(filename));
                isLocal = true;
            } else {
                // set the source of the media player to a manual URL or
                // path
                createMediaPlayerIfNeeded();
                mPlayer.setDownLoadWhilePlaying(downloadWhenPlaying);
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setDataSource(url, cachedFile, id);
                isLocal = false;
                if (!TextUtils.isEmpty(url)) {
                    mIsStreaming = url.startsWith("http:") || url.startsWith("https:");
                }
            }

            mLocalFile = filename;
            mUrl = url;
            musicId = id;
            mCacheFile = cachedFile;
            mState = MusicState.Preparing;
            notifyMusicState(mState);
            // setUpAsForeground();

            // Use the media button APIs (if available) to register
            // ourselves
            // for media button
            // events

            MediaButtonHelper.registerMediaButtonEventReceiverCompat(mAudioManager, mMediaButtonReceiverComponent);


            // starts preparing the media player in the background. When
            // it's
            // done, it will call
            // our OnPreparedListener (that is, the onPrepared() method on
            // this
            // class, since we set
            // the listener to 'this').
            //
            // Until the media player is prepared, we *cannot* call start()
            // on
            // it!
            mPlayer.prepareAsync();

            // If we are streaming from the internet, we want to hold a Wifi
            // lock, which prevents
            // the Wifi radio from going to sleep while the song is playing.
            // If,
            // on the other hand,
            // we are *not* streaming, we want to release the lock if we
            // were
            // holding it before.
            if (mIsStreaming) {
                mWifiLock.acquire();
            } else if (mWifiLock.isHeld()) {
                mWifiLock.release();
            }
        } catch (IOException ex) {
            Log.e("MusicService", "IOException playing next song: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IllegalStateException e) {
            Log.e("MusicService", "IllegalStateException playing next song: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 音频放弃焦点
     */
    void giveUpAudioFocus() {
        if (mAudioFocus == AudioFocus.Focused && mAudioFocusHelper != null && mAudioFocusHelper.abandonFocus()) {
            mAudioFocus = AudioFocus.NoFocusNoDuck;
        }
    }

    /**
     * 暂停播放
     *
     * @param updateNoti
     */
    void pause(boolean updateNoti) {
        if (mState == MusicState.Playing || mState == MusicState.Preparing) {
            // Pause media player and cancel the 'foreground service' state.
            mState = MusicState.Paused;

            if (mPlayer != null) {
                mPlayer.pause();
            }
            notifyMusicState(mState);
            relaxResources(false, false); // while paused, we always retain the

//            if (updateNoti) {
//                notigyBBAction(BBLogicAction.notification_update);
//            }
        }
    }

    /**
     * 停止播放(非暂停)
     *
     * @param updateNotify 更新通知
     */
    void stop(boolean updateNotify) {
        if (mState == MusicState.Playing || mState == MusicState.Paused || mState == MusicState.Preparing) {
            mState = MusicState.Stopped;
            if (updateNotify) {
                relaxResources(true, true);
                stopForeground(true);
                mIsForeground = false;
            } else {
                relaxResources(true, false);
            }
        }
        notifyMusicState(mState);
    }

    private void readyToSeek() {
        if (callback != null) {
            callback.onSeekToLast();
        }
    }

    /**
     * 注册停止播放广播
     */
    private void registerStopReceiver() {
        try {
            if (mStopReceiver == null) {
                mStopReceiver = new StopReceiver();
                IntentFilter filter = new IntentFilter();
                filter.addAction(CommonUI.ACTION_STOP_MUSIC_SERVICE);
                filter.addAction(CommonUI.ACTION_PAUSE_MUSIC_SERVICE);
                registerReceiver(mStopReceiver, filter);
            }
        } catch (Exception ignore) {
        }
    }

    /**
     * 注销停止播放广播
     */
    private void unRegisterStopReceiver() {
        if (mStopReceiver != null) {
            unregisterReceiver(mStopReceiver);
            mStopReceiver = null;
        }
    }

    /**
     * 播放
     */
    private void play() {
        tryToGetAudioFocus();

        // actually play the song

        if (mState == MusicState.Stopped) {
            // If we're stopped, just go ahead to the next song and start
            // playing
            notifyControlAction(ControlAction.ACTION_PLAY);
        } else if (mState == MusicState.Paused) {
            recover();
        }
    }

    /**
     * 上一首
     */
    private void prev() {
        notifyControlAction(ControlAction.ACTION_PREV);
    }

    /**
     * 下一首
     */
    private void next() {
        notifyControlAction(ControlAction.ACTION_NEXT);
    }

    /**
     * 更新音乐播放状态
     *
     * @param state
     */
    private void notifyMusicState(MusicState state) {
        if (callback != null) {
            callback.onStateChanged(state);
        }
    }

    /**
     * 更新控制状态
     *
     * @param action
     */
    private void notifyControlAction(String action) {
        if (callback != null) {
            callback.onControlAction(action);
        }
    }

    private void processTogglePlaybackRequest() {
        if (mState == MusicState.Paused || mState == MusicState.Stopped) {
            play();
        } else {
            pause(false);
        }
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    class StopReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                if (CommonUI.ACTION_STOP_MUSIC_SERVICE.equals(action)) {
                    stop(false);
                } else if (CommonUI.ACTION_PAUSE_MUSIC_SERVICE.equals(action)) {
                    pause(false);
                }
            }
        }
    }
}
