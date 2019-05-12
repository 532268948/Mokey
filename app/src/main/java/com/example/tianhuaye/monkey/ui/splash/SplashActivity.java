package com.example.tianhuaye.monkey.ui.splash;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lib_common.base.ExecutorThreadService;
import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.common.Constant;
import com.example.lib_common.service.MyJobService;
import com.example.lib_common.util.SharedPreferencesUtil;
import com.example.module_habit.ui.notify.AlarmNotifyActivity;
import com.example.tianhuaye.monkey.R;
import com.example.tianhuaye.monkey.contract.SplashContract;
import com.example.tianhuaye.monkey.presenter.SplashPresenter;
import com.example.tianhuaye.monkey.ui.activity.MainActivity;

import static android.app.Notification.VISIBILITY_SECRET;


/**
 * @author tianhuaye
 */
public class SplashActivity extends BaseActivity<SplashContract.View, SplashPresenter<SplashContract.View>> implements SplashContract.View {

    /**
     * 是否第一次打开app
     */
    private boolean first = false;
    private NotificationManager manager;
    private ComponentName jobService;
    private JobScheduler scheduler;

    @Override
    protected SplashPresenter<SplashContract.View> createPresenter() {
        return new SplashPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initUIParams() {
        super.initUIParams();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        jobService = new ComponentName(this, MyJobService.class);
        scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        createJob();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        ExecutorThreadService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Constant.SPLASH_DURING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gotoMainActivity();
            }
        });
        sharedPreferencesUtil = new SharedPreferencesUtil(this, "monkey");
        Constant.USER_ID = (Long) sharedPreferencesUtil.getSharedPreference("user_id", 0L);
        Constant.TOKEN = (String) sharedPreferencesUtil.getSharedPreference("token", "");
        //获取用户信息
        mPresenter.getUserInformation(Constant.TOKEN);
        first = (Boolean) sharedPreferencesUtil.getSharedPreference("first", true);
        if (first) {
            mPresenter.initFirst();
            updateFirst();
        }
        sendNotification();
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateFirst() {
        sharedPreferencesUtil.put("first", false);
    }

    private void sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //26及以上
            NotificationChannel notificationChannel = new NotificationChannel("id", "name", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.canBypassDnd();//可否绕过请勿打扰模式
            notificationChannel.canShowBadge();//桌面lanchener显示角标
            notificationChannel.enableLights(true);//闪光
            notificationChannel.shouldShowLights();//闪光
            notificationChannel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            notificationChannel.enableVibration(true);//是否允许震动
            notificationChannel.setVibrationPattern(new long[]{100, 100, 200});//设置震动方式（事件长短）
            notificationChannel.getAudioAttributes();//获取系统响铃配置
            notificationChannel.getGroup();//获取消息渠道组
            notificationChannel.setBypassDnd(true);
            notificationChannel.setDescription("description");
            notificationChannel.setLightColor(Color.GREEN);//制定闪灯是灯光颜色
            notificationChannel.setShowBadge(true);
            manager.createNotificationChannel(notificationChannel);
//            RemoteViews contentViews = new RemoteViews(getPackageName(),R.layout.notify_alarm);
            Notification.Builder builder = new Notification.Builder(getApplicationContext(), "id");
            builder.setSmallIcon(R.mipmap.ic_launcher);
//            builder.setCustomContentView(contentViews);
            builder.setAutoCancel(true);
            builder.setChannelId("id");
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle("小猿睡眠 闹钟服务");
//            builder.setContentText("内容8.0");
            builder.setNumber(3);
            builder.setOngoing(true);
            Intent intent = new Intent(this, AlarmNotifyActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            manager.notify(2, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setAutoCancel(true);
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle("题目");
            builder.setContentText("内容");
            builder.setOngoing(true);
            Intent intent = new Intent(this, AlarmNotifyActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            manager.notify(2, builder.build());
        }

    }

    private void createJob() {
        JobInfo.Builder builder = new JobInfo.Builder(1, jobService);
        JobInfo jobInfo = builder.setPeriodic(Constant.MINUTE) // 每隔15分钟运行一次
                .setMinimumLatency(0) // 设置任务运行最少延迟时间
                .setPersisted(true) // 设备重新启动之后你的任务是否还要继续运行
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // 设置网络条件（不是蜂窝网络( 比方在WIFI连接时 )时任务才会被运行）
                .setRequiresCharging(true) // 设置是否充电的条件
                .setRequiresDeviceIdle(false) // 设置手机是否空闲的条件
                .setRequiresCharging(true) // 这种方法告诉你的应用，仅仅有当设备在充电时这个任务才会被运行。
                .setRequiresDeviceIdle(true) //这种方法告诉你的任务仅仅有当用户没有在使用该设备且有一段时间没有使用时才会启动该任务。
                .build();
        scheduler.schedule(jobInfo);
    }
}
