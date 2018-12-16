package com.example.lib_common.util;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date :   2018/12/16
 * @time :   12:30
 * @email :  15869107730@163.com
 * @note :   全局消息池管理类
 */
public class MessageLooperMgr {

    public static MessageLooper mBTMessageLooper = new MessageLooper(new Handler());

    /**
     * 监听器容器（以Activity或Fragment作为分类标准）
     */
    private static class ListenerContainer {
        Activity activity;
        Fragment fragment;
        ArrayList<MessageLooper.OnMessageListener> listeners;

        ListenerContainer(Activity activity) {
            this.activity = activity;
        }

        ListenerContainer(Fragment fragment) {
            this.fragment = fragment;
        }

        public void clear() {
            activity = null;
            fragment = null;
            if (listeners != null) {
                MessageLooper msgLooper = mBTMessageLooper;
                for (MessageLooper.OnMessageListener listener : listeners) {
                    if (listener == null) {
                        continue;
                    }
                    if (msgLooper != null) {
                        msgLooper.unregisterReceiver(listener);
                    }
                }

                listeners.clear();
                listeners = null;
            }
        }

        public boolean remove(MessageLooper.OnMessageListener l) {
            MessageLooper msgLooper = mBTMessageLooper;
            if (msgLooper != null) {
                msgLooper.unregisterReceiver(l);
            }
            return listeners != null && listeners.remove(l);
        }
    }

    /**
     * 注册监听器
     *
     * @param cmd 监听器标志
     * @param l   监听器
     */
    public static void registerMessageReceiver(String cmd, MessageLooper.OnMessageListener l) {
        MessageLooper msgLooper = mBTMessageLooper;
        if (msgLooper != null) {
            msgLooper.registerReceiver(cmd, l);
        }
    }

    /**
     * 注销监听器
     *
     * @param cmd 监听器标志
     * @param l   监听器
     */
    public static void unRegisterMessageReceiver(String cmd, MessageLooper.OnMessageListener l) {
        MessageLooper msgLooper = mBTMessageLooper;
        if (msgLooper != null) {
            msgLooper.unregisterReceiver(cmd, l);
        }
    }

    /**
     * 监听容器集合
     */
    private static List<ListenerContainer> containers = new ArrayList<>();


    /**
     * 在该方法中注册一些任务的回调操作(Activity)
     */
    public static void registerMessageReceiver(Activity activity, String cmd, MessageLooper.OnMessageListener l) {
        MessageLooper msgLooper = mBTMessageLooper;
        if (msgLooper != null) {
            msgLooper.registerReceiver(cmd, l);
        }
        ListenerContainer container = getContainer(activity);
        if (container == null) {
            container = new ListenerContainer(activity);
            containers.add(container);
        }
        if (container.listeners == null) {
            container.listeners = new ArrayList<>();
        }
        container.listeners.add(l);
    }


    /**
     * 在该方法中注册一些任务的回调操作(Fragment)
     */
    public static void registerMessageReceiver(Fragment fragment, String cmd, MessageLooper.OnMessageListener l) {
        MessageLooper msgLooper = mBTMessageLooper;
        if (msgLooper != null) {
            msgLooper.registerReceiver(cmd, l);
        }
        ListenerContainer container = getContainer(fragment);
        if (container == null) {
            container = new ListenerContainer(fragment);
            containers.add(container);
        }
        if (container.listeners == null) {
            container.listeners = new ArrayList<>();
        }
        container.listeners.add(l);
    }

    /**
     * 注销所有关于此activity的任务回调 （并非取消任务）
     *
     * @param activity
     */
    public static void onUnregisterMessageReceiver(Activity activity) {
        ListenerContainer container = removeContainer(activity);
        if (container != null) {
            container.clear();
        }
    }

    /**
     * 注销所有关于此fragment的任务回调 （并非取消任务）
     *
     * @param fragment
     */
    public static void onUnregisterMessageReceiver(Fragment fragment) {
        ListenerContainer container = removeContainer(fragment);
        if (container != null) {
            container.clear();
        }
    }

    /**
     * 注销关于此activity l 的任务回调（并非取消任务）
     *
     * @param activity
     */
    public static void onUnregisterMessageReceiver(Activity activity, MessageLooper.OnMessageListener l) {
        ListenerContainer container = getContainer(activity);
        if (container != null) {
            container.remove(l);
        }
    }

    /**
     * 注销关于此fragment l 的任务回调（并非取消任务）
     *
     * @param fragment
     */
    public static void onUnregisterMessageReceiver(Fragment fragment, MessageLooper.OnMessageListener l) {
        ListenerContainer container = getContainer(fragment);
        if (container != null) {
            container.remove(l);
        }
    }

    /**
     * 移除Activity监听容器
     *
     * @param activity
     * @return
     */
    private static ListenerContainer removeContainer(Activity activity) {
        try {
            ListenerContainer container;
            int index = -1;
            for (int i = 0; i < containers.size(); i++) {
                container = containers.get(i);
                if (container == null) {
                    continue;
                }
                if (container.activity != null && container.activity == activity) {
                    index = i;
                    break;
                }
            }

            if (index >= 0) {
                return containers.remove(index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 移除Fragment监听容器
     *
     * @param fragment
     * @return
     */
    private static ListenerContainer removeContainer(Fragment fragment) {
        ListenerContainer container;
        int index = -1;
        for (int i = 0; i < containers.size(); i++) {
            container = containers.get(i);
            if (container == null) {
                continue;
            }
            if (container.fragment != null && container.fragment == fragment) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            return containers.remove(index);
        }

        return null;
    }

    /**
     * 获取Activity监听容器
     *
     * @param activity
     * @return
     */
    private static ListenerContainer getContainer(Activity activity) {
        try {
            for (ListenerContainer container : containers) {
                if (container == null) {
                    continue;
                }
                if (container.activity != null && container.activity == activity) {
                    return container;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Fragment监听容器
     *
     * @param fragment
     * @return
     */
    private static ListenerContainer getContainer(Fragment fragment) {
        try {
            for (ListenerContainer container : containers) {
                if (container == null) {
                    continue;
                }
                if (container.fragment != null && container.fragment == fragment) {
                    return container;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
