package com.example.lib_common.base;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lib_common.BuildConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/23
 * @time : 20:29
 * @email : 15869107730@163.com
 * @note : 公用线程池
 */
public class ExecutorThreadService {
    /**
     * 暂时定位3个+3
     */
    private static final int POOL_SIZE = 6;
    private static ExecutorService service;
    private static AtomicInteger counter = new AtomicInteger();
    private static LinkedBlockingQueue<Runnable> waitingQueueList;

    private static void initService() {
        if (waitingQueueList == null) {
            waitingQueueList = new LinkedBlockingQueue<>();
        }
        if (service == null || service.isShutdown()) {
            service = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 0L, TimeUnit.MICROSECONDS, waitingQueueList, new
                    ThreadFactory() {
                        @Override
                        public Thread newThread(@NonNull Runnable r) {
                            return new Thread(r, "BT-Thread-" + counter.getAndIncrement());
                        }
                    });
        }
    }

    public static void execute(Runnable task) {
        initService();
        service.execute(task);
        if (BuildConfig.DEBUG) {
            Log.d("BTExecutorService", "waitingQueueList size : " + waitingQueueList.size());
        }
    }

    static void shutDown() {
        if (waitingQueueList != null) {
            waitingQueueList.clear();
        }
        if (service != null) {
            service.shutdownNow();
            service = null;
        }
    }
}
