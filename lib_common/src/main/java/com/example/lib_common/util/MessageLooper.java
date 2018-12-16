package com.example.lib_common.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date :   2018/12/16
 * @time :   12:09
 * @email :  15869107730@163.com
 * @note :   全局消息池
 */
public class MessageLooper {

    private final static String TAG = "BTMessageLooper";

    public interface OnMessageListener {
        void onMessage(Message msg);
    }

    private Map<String, Vector<OnMessageListener>> mMessageReceiver = new Hashtable<>();

    private Handler mHandler;

    public MessageLooper(Handler mHandler) {
        this.mHandler = mHandler;
    }

    /**
     * 注册消息接收器
     *
     * @param cmd 消息标志
     * @param l   监听器
     */
    public void registerReceiver(String cmd, OnMessageListener l) {
        if (cmd == null || l == null) {
            return;
        }
        Vector<OnMessageListener> listeners = mMessageReceiver.get(cmd);
        if (listeners == null) {
            listeners = new Vector<>();
            listeners.add(l);
            mMessageReceiver.put(cmd, listeners);
        } else {
            listeners.add(l);
        }
    }

    /**
     * 注销消息监听器（某个类别中的一个）
     * @param cmd 消息标志
     * @param l   监听器
     */
    public void unregisterReceiver(String cmd, OnMessageListener l) {
        Vector<OnMessageListener> listeners = mMessageReceiver.get(cmd);
        if (listeners != null) {
            listeners.remove(l);
        }
    }

    public void unregisterReceiver(OnMessageListener l) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                //because of Iterator is not synchronized , so use foreach 在遍历的时候不能同时remove，否则回报多线程安全问题
                String removeKey = null;
                for (Map.Entry<String, Vector<OnMessageListener>> entry : mMessageReceiver.entrySet()) {
                    if (entry == null) {
                        continue;
                    }
                    String key = entry.getKey();
                    Vector<OnMessageListener> list = mMessageReceiver.get(key);
                    if (list == null) {
                        continue;
                    }
                    if (list.remove(l)) {
                        if (list.isEmpty()) {
                            removeKey = key;
                        }
                        break;
                    }
                }
                if (!TextUtils.isEmpty(removeKey)) {
                    mMessageReceiver.remove(removeKey);
                }
            } else {
                postRemove(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @param cmd
     * @param msg
     * @return
     */
    public boolean sendMessage(String cmd, Message msg) {
        Vector<OnMessageListener> listeners = mMessageReceiver.get(cmd);
        if (listeners != null) {
            OnMessageListener listener;
            for (int i = 0; i < listeners.size(); i++) {
                listener = listeners.get(i);
                if (listener != null) {
                    listener.onMessage(msg);
                }
            }
        }

        return true;
    }

    private void postRemove(final OnMessageListener l) {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //because of Iterator is not synchronized , so use foreach 在遍历的时候不能同时remove，否则回报多线程安全问题
                    try {
                        String removeKey = null;
                        for (Map.Entry<String, Vector<OnMessageListener>> entry : mMessageReceiver.entrySet()) {
                            if (entry == null) {
                                continue;
                            }
                            String key = entry.getKey();
                            Vector<OnMessageListener> list = mMessageReceiver.get(key);
                            if (list == null) {
                                continue;
                            }
                            if (list.remove(l)) {
                                if (list.isEmpty()) {
                                    removeKey = key;
                                }
                                break;
                            }
                        }
                        if (!TextUtils.isEmpty(removeKey)) {
                            mMessageReceiver.remove(removeKey);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
