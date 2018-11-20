package com.example.lib_common.util;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.lib_common.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * project: Monkey
 * author : 叶天华
 * date   : 2018/11/18
 * time   : 16:57
 * email  : 15869107730@163.com
 * note   :
 */
public class ViewUtil {
    /**
     * 创建一个防止快速点击的ClickListener
     *
     * @param listener 原有ClickListener
     * @return 包装后的ClickListener
     */
    public static View.OnClickListener createInternalClickListener(View.OnClickListener listener) {
        return createInternalClickListener(listener, 800);
    }

    /**
     * 创建一个防止快速点击的ClickListener
     *
     * @param listener     原有ClickListener
     * @param internalTime 点击间隔时间
     * @return 包装后的ClickListener
     */
    public static View.OnClickListener createInternalClickListener(View.OnClickListener listener, long internalTime) {
        ClickInvocationHandler invocationHandler = new ClickInvocationHandler(listener, internalTime);
        return (View.OnClickListener) Proxy.newProxyInstance(listener.getClass().getClassLoader(), listener.getClass
                ().getInterfaces(), invocationHandler);
    }
    /**
     * 控制EmptyView的显示
     */
    public static void setEmptyViewVisible(View view, Context context, boolean visible, boolean
            networkException) {
        String prompt;
        if (networkException) {
            prompt = context.getString(R.string.common_empty_tip_1);
        } else {
            prompt = context.getString(R.string.common_empty_tip_2);
        }
        setEmptyViewVisible(view, context, visible, networkException, prompt);
    }

    /**
     * 控制EmptyView的显示
     */
    public static void setEmptyViewVisible(View view, Context context, boolean visible, boolean networkException,String text) {
        if (null == view || null == context)
            return;

        if (!visible) {
            view.setVisibility(View.GONE);
            return;
        }

        TextView promptView = (TextView) view.findViewById(R.id.tv_empty_prompt);
        if (null == promptView) {
            view.setVisibility(View.VISIBLE);
            return;
        }

        String prompt;
        if (networkException) {
            prompt = context.getString(R.string.common_empty_tip_1);
        } else {
            prompt = context.getString(R.string.common_empty_tip_2);
        }

        if (!TextUtils.isEmpty(text) && !networkException) {
            prompt = text;
        }
        promptView.setText(prompt);
        promptView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_empty_no_data, 0, 0);
        view.setVisibility(View.VISIBLE);
    }
    /**
     * 设置view可见
     */
    public static void setViewVisible(View view) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置view不可见
     */
    public static void setViewInVisible(View view) {
        if (view != null && view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置view隐藏
     */
    public static void setViewGone(View view) {
        if (view != null && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    private static class ClickInvocationHandler implements InvocationHandler {

        private Object target;
        private long clickTime = 0;
        private long internalTime = 800;
        private static String onClickName;

        public ClickInvocationHandler(Object target) {
            this(target, 800);
        }

        public ClickInvocationHandler(Object target, long internalTime) {
            this.target = target;
            this.internalTime = internalTime;
            if (TextUtils.isEmpty(onClickName)) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                };
                Method[] methods = listener.getClass().getDeclaredMethods();
                if (methods == null || methods.length == 0) {
                    onClickName = "onClick";
                } else {
                    onClickName = methods[0].getName();
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals(onClickName)) {
                long nowTime = SystemClock.elapsedRealtime();
                Object result = null;
                if (nowTime - clickTime >= internalTime) {
                    result = method.invoke(target, args);
                    clickTime = nowTime;
                }
                return result;
            } else {
                return method.invoke(target, args);
            }
        }
    }
}
