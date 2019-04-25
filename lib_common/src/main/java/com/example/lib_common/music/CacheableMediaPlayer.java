package com.example.lib_common.music;

import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.example.lib_common.R;
import com.example.lib_common.base.BaseApplication;
import com.example.lib_common.util.FileUtil;
import com.example.lib_common.util.NumberUtil;
import com.example.lib_common.util.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

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
    private static final int GET_LOCAL_URL_DONE = 4;
    private final int SOCKET_TIME_OUT = 5000;

    private OnCachedProgressUpdateListener mOnCachedProgressUpdateListener;
    private MusicControlInterface musicControlInterface;

    /**
     * 本地端口
     */
    private int mLocalPort;
    //
    private String mLocalHost;
    /**
     * 是否边播放边下载
     */
    private boolean mDownloadWhilePlaying = false;

    /**
     * 是否为缓存文件
     */
    private boolean mCacheFile;

    /**
     * 缓存文件地址
     */
    private String mCacheFileName = "";
    /**
     * 音频id
     */
    private long musicId;
    /**
     * 要缓存的文件总长度
     */
    private long mFileTotalLength = 0;

    /**
     * 已缓存的文件长度
     */
    private long mCachedFileLength = 0;

    /**
     * 当前的音乐缓冲值（seekbar上的缓冲值）
     */
    public int mCurrentMusicCachedProgress = 0;

    /**
     * 当前音乐播放进度
     */
    public int mCurrentPlayDegree = 0;

    /**
     * 远程音乐地址
     */
    private String mUrl = "";

    /**
     * 正在缓存的网络音乐地址
     */
    public static List<String> bufferingMusicUrlList = new ArrayList<>();

    /**
     * 最新的代理ID
     */
    public static long lastProxyId = 0;
    /**
     * 当前代理ID
     */
    public long currProxyId = 0;

    /**
     * 代理播放失败了
     */
    public boolean proxyFail = false;

    /**
     * 代理下载子线程
     */
    private Thread mProxyThread;

    private ServerSocket mLocalServer = null;
    private SocketAddress mServerAddress;
    private String mRemoteHost;
    private String mRemoteHostAndPort = "";
    /**
     * 音乐的远程socket请求地址
     */
    private String trueSocketRequestInfoStr = "";
    private int mRemotePort = -1;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == GET_LOCAL_URL_DONE) {
                String localUrl = (String) msg.obj;
                try {
                    String path = checkUrl(localUrl);
                    if (!TextUtils.isEmpty(path)) {
                        setDataSource(path);
                    } else {
                        ToastUtil.showShortToastMessage(BaseApplication.mContext, R.string.music_error);
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                preparePlay();
            }
        }
    };

    private void preparePlay() {
        try {
            super.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String checkUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            try {
                int start = url.lastIndexOf("/");
                int end = url.lastIndexOf(".");
                String startUrl = url.substring(0, start + 1);
                String needEncodeStr = url.substring(start + 1, end);
                String endStr = url.substring(end);
                String encodedStr = needEncodeStr;
                encodedStr = URLEncoder.encode(needEncodeStr, "UTF-8");
                return startUrl + encodedStr + endStr;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 设置是否边播放边下载
     *
     * @param downloadWhilePlaying
     */
    public void setDownLoadWhilePlaying(boolean downloadWhilePlaying) {
        mDownloadWhilePlaying = downloadWhilePlaying;
    }

    /**
     * @param path          音频url
     * @param cacheFilename 缓存文件地址(携带精确地址)
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalStateException
     */
    public void setDataSource(String path, String cacheFilename, long id)
            throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        musicId = id;
        mUrl = path;
        mCacheFileName = cacheFilename;
        try {
            //缓存文件不为空或非边下边播
            if (!TextUtils.isEmpty(cacheFilename)) {
                File file = new File(cacheFilename);
                //文件存在
                if (file.isFile() && file.exists()) {
                    mCacheFile = true;
                    setDataSource(cacheFilename);
                } else {
                    mCacheFile = false;
                    //边下边播
                    if (mDownloadWhilePlaying) {
                        try {
                            mLocalHost = ProxyConfig.LOCAL_IP_ADDRESS;
                            mLocalServer = new ServerSocket(0, 1, InetAddress.getByName(mLocalHost));
                            mLocalPort = mLocalServer.getLocalPort();
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                            mCacheFile = false;
                        } catch (IOException e) {
                            e.printStackTrace();
                            mCacheFile = false;
                        }
                    } else {
                        setDataSource(path);
                    }
                }
            } else { //为空且为边下边播
                mCacheFile = false;
                if (mDownloadWhilePlaying) {
                    try {
                        mLocalHost = ProxyConfig.LOCAL_IP_ADDRESS;
                        mLocalServer = new ServerSocket(0, 1, InetAddress.getByName(mLocalHost));
                        mLocalPort = mLocalServer.getLocalPort();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                        mCacheFile = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                        mCacheFile = false;
                    }
                } else {
                    setDataSource(path);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        //缓存文件为空&&非缓存文件&&边下边播
        if (!mCacheFile && mDownloadWhilePlaying) {
            mProxyThread = new Thread("MediaPlayer socket monitor") {
                @Override
                public void run() {
                    startProxy();
                }
            };
            mProxyThread.start();
            getLocalURL(mUrl);
        } else {
            super.prepareAsync();
        }
    }

    /**
     * 把网络URL转为本地URL，127.0.0.1替换网络域名,且设置远程的socket连接地址
     *
     * @param url 网络URL
     */
    private void getLocalURL(final String url) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                String mediaUrl = url;

                if (mDownloadWhilePlaying){
                    bufferingMusicUrlList.add(mediaUrl);
                }
                
                String localUrl;
                URI originalURI = null;
                try {
                    originalURI = URI.create(mediaUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (originalURI != null) {
                    mRemoteHost = originalURI.getHost();
                    if (originalURI.getPort() != -1) {
                        mServerAddress = new InetSocketAddress(mRemoteHost, originalURI.getPort());
                        mRemotePort = originalURI.getPort();
                        localUrl = mediaUrl.replace(mRemoteHost + ":" + originalURI.getPort(), mLocalHost + ":" +
                                mLocalPort);
                    } else {
                        mServerAddress = new InetSocketAddress(mRemoteHost, ProxyConfig.HTTP_PORT);
                        mRemotePort = -1;
                        localUrl = mediaUrl.replace(mRemoteHost, mLocalHost + ":" + mLocalPort);
                    }

                    if (mHandler != null) {
                        Message msg = mHandler.obtainMessage(GET_LOCAL_URL_DONE);
                        msg.obj = localUrl;
                        mHandler.sendMessage(msg);
                    }
                }
            }

            ;
        };
        thread.start();
    }

    /**
     * 启动代理服务器
     */
    public void startProxy() {

        try {
            //监听MediaPlayer的请求，MediaPlayer->代理服务器
            Socket localSocket = mLocalServer.accept();

            //获得真实请求信息
            getTrueSocketRequestInfo(localSocket);

            //保证创建了远程socket地址再进行下一步
            while (mServerAddress == null) {
                sleep(25);
            }

            //发送真实socket请求，并返回remote_socket
            Socket remoteSocket = sendRemoteRequest();

            //处理真实请求信息
            processTrueRequestInfo(remoteSocket, localSocket);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //最后释放本地代理serversocket
            if (mLocalServer != null) {
                try {
                    mLocalServer.close();
                    mLocalServer = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理真实请求信息, 把网络服务器的反馈发到MediaPlayer，网络服务器->代理服务器->MediaPlayer
     *
     * @param remoteSocket
     * @param localSocket
     */
    public void processTrueRequestInfo(Socket remoteSocket, Socket localSocket) {
        //如果要写入本地文件的实例声明
        FileOutputStream fileOutputStream = null;
        File theFile = null;

        try {
            //获取音乐网络数据
            InputStream in_remoteSocket = null;
            OutputStream out_localSocket = null;

                in_remoteSocket = remoteSocket.getInputStream();
                if (in_remoteSocket == null) {
                    return;
                }

                out_localSocket = localSocket.getOutputStream();
                if (out_localSocket == null) {
                    return;
                }
            

            //如果要写入文件，配置相关实例
            if (mDownloadWhilePlaying) {
                File dirs = new File(Environment.getExternalStorageDirectory() + File.separator + "monkey_music");
                if (!dirs.exists()) {
                    dirs.mkdirs();
                }
                theFile = new File(getTmpFile(Long.toString(musicId)));
                fileOutputStream = new FileOutputStream(theFile);
            }


            try {
                int readLength;
                byte[] remote_reply = new byte[4096];
                //是否循环中第一次获得数据
                boolean firstData = true;

                //当从远程还能取到数据且播放器还没切换另一首网络音乐
                while ((readLength = in_remoteSocket.read(remote_reply, 0, remote_reply.length)) != -1 && currProxyId == lastProxyId) {

                    //首先从数据中获得文件总长度
                    try {
                        if (firstData) {
                            firstData = false;
                            String str = new String(remote_reply, "utf-8");
                            Pattern pattern = Pattern.compile("Content-Length:\\s*(\\d+)");
                            Matcher matcher = pattern.matcher(str);
                            if (matcher.find()) {
                                //获取数据的大小
                                mFileTotalLength = Long.parseLong(matcher.group(1));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //把远程sokcet拿到的数据用本地socket写到mediaplayer中播放
                    try {
                        out_localSocket.write(remote_reply, 0, readLength);
                        out_localSocket.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //计算当前播放时，其在seekbar上的缓冲值,并刷新进度条
                    try {
                        mCachedFileLength += readLength;
                        if (mFileTotalLength > 0 && currProxyId == lastProxyId) {
                            mCurrentMusicCachedProgress = (int) (NumberUtil.div(mCachedFileLength, mFileTotalLength, 5) * 100);
                            if (mOnCachedProgressUpdateListener != null && mCurrentMusicCachedProgress <= 100) {
                                mOnCachedProgressUpdateListener.updateCachedProgress(mCurrentMusicCachedProgress);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //如果需要缓存数据到本地，就缓存到本地
                    if (mDownloadWhilePlaying) {
                        try {
                            if (fileOutputStream != null) {
                                fileOutputStream.write(remote_reply, 0, readLength);
                                fileOutputStream.flush();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                //如果是因为切换音乐跳出循环的，当前音乐播放进度，小于 seekbar最大值的1/4,就把当前音乐缓存在本地的数据清除了
                if (currProxyId != lastProxyId && mCurrentPlayDegree < 25) {
                    bufferingMusicUrlList.remove(mUrl);
                    if (theFile != null) {
                        FileUtil.deleteFile(theFile.getPath());
                    }
                }

            } catch (Exception e) {
                if (theFile != null) {
                    FileUtil.deleteFile(theFile.getPath());
                }
                bufferingMusicUrlList.remove(mUrl);

            } finally {
                Log.e("CacheableMediaPlayer", "processTrueRequestInfo: 111111111");
                in_remoteSocket.close();
                out_localSocket.close();
                if (fileOutputStream != null) {
                    fileOutputStream.close();
//                    Log.e("CacheableMediaPlayer", "processTrueRequestInfo: 222222");
                    //音频文件缓存完后处理
                    if (theFile != null && FileUtil.checkFileExist(theFile.getPath())) {
//                        Log.e("CacheableMediaPlayer", "processTrueRequestInfo: 333333");
                        conver2RightAudioFile(theFile);
//                        Log.e("CacheableMediaPlayer", "processTrueRequestInfo: musicId:"+musicId+"缓存成功");
                        if (musicControlInterface != null) {
                            musicControlInterface.updateBufferFinishMusicPath(theFile.getPath(),musicId);
                            bufferingMusicUrlList.remove(mUrl);
                        }
                    }

                }
                localSocket.close();
                remoteSocket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
//            if (BuildConfig.DEBUG){
//                Log.e("CacheableMediaPlayer", "processTrueRequestInfo: "+e.toString());
//            }
            if (theFile != null) {
                FileUtil.deleteFile(theFile.getPath());
            }
            bufferingMusicUrlList.remove(mUrl);
        }
    }

    /**
     * 转换为正确的音频文件
     *
     * @param file
     */
    public void conver2RightAudioFile(File file) {
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            inputStream = new FileInputStream(file);
            int read = 0;
            while (read > -1) {
                int newRead = inputStream.read();
                if (read == 0 && newRead == 0) {
                    byte[] bs = new byte[inputStream.available() + 2];
                    inputStream.read(bs, 2, bs.length - 2);
                    fos = new FileOutputStream(file);
                    fos.write(bs);
                    fos.flush();
                    break;
                }
                read = newRead;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CacheableMediaPlayer", "conver2RightAudioFile: 3333333333");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                Log.e("CacheableMediaPlayer", "conver2RightAudioFile:222");
            }
        }
    }

    /**
     * 通过远程socket连接远程请求，并返回remot_socket
     *
     * @return
     * @throws Exception
     */
    public Socket sendRemoteRequest() throws Exception {
        //创建远程socket用来请求网络数据
        Socket remoteSocket = new Socket();
        remoteSocket.connect(mServerAddress, SOCKET_TIME_OUT);
        remoteSocket.getOutputStream().write(trueSocketRequestInfoStr.getBytes());
        remoteSocket.getOutputStream().flush();
        return remoteSocket;
    }

    /**
     * 获得真实的socket请求信息
     *
     * @param localSocket
     * @throws Exception
     */
    public void getTrueSocketRequestInfo(Socket localSocket) throws Exception {
        InputStream in_localSocket = localSocket.getInputStream();
        //保存MediaPlayer的真实HTTP请求
        String trueSocketRequestInfoStr = "";

        byte[] local_request = new byte[1024];
        while (in_localSocket.read(local_request) != -1) {
            String str = new String(local_request);
            trueSocketRequestInfoStr = trueSocketRequestInfoStr + str;

            if (trueSocketRequestInfoStr.contains("GET") && trueSocketRequestInfoStr.contains("\r\n\r\n")) {
                //把request中的本地ip改为远程ip
                trueSocketRequestInfoStr = trueSocketRequestInfoStr.replace(ProxyConfig.LOCAL_IP_ADDRESS + ":" + mLocalPort, mRemoteHostAndPort);
                this.trueSocketRequestInfoStr = trueSocketRequestInfoStr;
                //如果用户拖动了进度条，因为拖动了滚动条还有Range则表示本地歌曲还未缓存完，不再保存
                if (trueSocketRequestInfoStr.contains("Range")) {
                    mDownloadWhilePlaying = false;
                }
                break;
            }
        }
    }

    /**
     * 获取缓存文件名字
     *
     * @param filename
     * @return
     */
    private String getTmpFile(String filename) {
        return (Environment.getExternalStorageDirectory() + File.separator + "monkey_music" + File.separator + filename + ".mp3");
    }

    public void setMusicCacheProgressListener(OnCachedProgressUpdateListener onCachedProgressUpdateListener) {
        this.mOnCachedProgressUpdateListener = onCachedProgressUpdateListener;
    }

    public interface OnCachedProgressUpdateListener {
        void updateCachedProgress(int progress);
    }

    public void setMusicCacheSuccessListener(MusicControlInterface musicCacheSuccessListener) {
        this.musicControlInterface = musicCacheSuccessListener;
    }

    public interface MusicControlInterface {
        void updateBufferFinishMusicPath(String localPath,long id);
    }


}
