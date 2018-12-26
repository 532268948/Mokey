package com.example.lib_common.music;

import android.media.MediaPlayer;
import android.text.TextUtils;

import com.example.lib_common.base.Constants;
import com.example.lib_common.util.StorageUtil;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/23
 * @time : 14:28
 * @email : 15869107730@163.com
 * @note :
 */
public class CacheableMediaPlayer extends MediaPlayer {

    public static final int MEDIA_ERROR_GET_SIZE = 13572468;
    public static final int STOP_DOWNLOAD_WHILE_PLAY = 9999;

    private int mTotalSize;
    /**
     * 本地端口
     */
    private int mLocalPort;

    private String mLocalHost;

    private String mCacheFilename;

    private String mUrl;

    /**
     * 是否边播放边下载
     */
    private boolean mDownloadWhilePlaying=false;

    /**
     * 是否为缓存文件
     */
    private boolean mCahceFile;

    private volatile boolean mExit = false;

    private ServerSocket mLocalServer = null;
    private SocketAddress mServerAddress;

    /**
     * 设置是否边播放边下载
     * @param downloadWhilePlaying
     */
    public void setDownLoadWhilePlaying(boolean downloadWhilePlaying) {
        mDownloadWhilePlaying = downloadWhilePlaying;
    }

    public void setDataSource(String path, String cacheFilename)
            throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {

        mTotalSize = -1;
        mExit = false;
        mCacheFilename = cacheFilename;
        if (!TextUtils.isEmpty(path) && path.startsWith("https:")) {
            mDownloadWhilePlaying = false;
        }
        mUrl = path;
        try {
            if (cacheFilename == null || !mDownloadWhilePlaying) {
                mCahceFile = false;
                setDataSource(path);
            } else {
                File file = new File(cacheFilename);
                if (file.isFile() && file.exists()) {
                    mCahceFile = false;
                    setDataSource(cacheFilename);
                } else {
                    mCahceFile = true;
                    String tempFilename = getTmpFile(cacheFilename);
                    file = new File(tempFilename);
                    if (!file.exists() && StorageUtil.getSDAvailableStore() < Constants.MUSIC_TEMP_MAX_SIZE) {
                        setDataSource(path);
                        mCahceFile = false;
                    } else {
                        try {
                            mLocalHost = ProxyConfig.LOCAL_IP_ADDRESS;
                            mLocalServer = new ServerSocket(0, 1, InetAddress.getByName(mLocalHost));
                            mLocalPort = mLocalServer.getLocalPort();
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                            mCahceFile = false;
                            setDataSource(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                            mCahceFile = false;
                            setDataSource(path);
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存文件名字
     * @param filename
     * @return
     */
    private String getTmpFile(String filename) {
        return (filename + ".mp-tmp");
    }

}
