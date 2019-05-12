package com.example.module_report.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.inter.OnItemClickListener;
import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.bean.BaseItem;
import com.example.lib_common.bean.DreamBean;
import com.example.lib_common.bean.ReportBean;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.DateUtil;
import com.example.lib_common.util.ShareSdkUtil;
import com.example.lib_common.util.StatusBarUtil;
import com.example.lib_common.util.ViewUtil;
import com.example.module_report.R;
import com.example.module_report.contract.ReportDetailContract;
import com.example.module_report.presenter.ReportDetailPresenter;
import com.example.module_report.view.SleepQualityView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tianhuaye
 */

@Route(path = Constant.Activity.ACTIVITY_REPORT_DETAIL)
public class ReportDetailActivity extends BaseActivity<ReportDetailContract.View, ReportDetailPresenter<ReportDetailContract.View>> implements ReportDetailContract.View, OnItemClickListener {

    private TitleBar mTitleBar;
    private NestedScrollView mNestedScrollView;
    private SleepQualityView mSleepQualityView;
    private RecyclerView mRecyclerView;
    private LinearLayout mFoldTitleLl;
    private TextView mDateTv;
    private TextView mStartTimeTv;
    private TextView mEndTimeTv;
    private TextView mStartTv;
    private TextView mEndTv;
    private TextView mSleepTv;
    private TextView mGradeTv;
    private TextView mStartStatusTv;
    private TextView mEndStatusTv;
    private TextView mSleepStatusTv;
    private TextView mGradeStatusTv;
    private TextView mDreamNumberTv;

    private List<BaseItem> mItems;
    private ReportDetailAdapter mAdapter;


    private ReportBean reportBean;
    private MediaPlayer mMediaPlayer;

    @Override
    protected ReportDetailPresenter<ReportDetailContract.View> createPresenter() {
        return new ReportDetailPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initUIParams() {
        super.initUIParams();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_report_detail;
    }

    @Override
    public void initIntent(Intent intent) {
        reportBean = (ReportBean) intent.getSerializableExtra("report");
    }

    @Override
    public void initView() {
        mTitleBar=findViewById(R.id.title_bar);
        mNestedScrollView=findViewById(R.id.nest_scroll_view);
        mSleepQualityView = findViewById(R.id.sleep_quality_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFoldTitleLl = findViewById(R.id.ll_fold);
        mDateTv = findViewById(R.id.tv_date);
        mStartTimeTv = findViewById(R.id.tv_start_time);
        mEndTimeTv = findViewById(R.id.tv_end_time);
        mStartTv = findViewById(R.id.tv_start);
        mEndTv = findViewById(R.id.tv_end);
        mSleepTv = findViewById(R.id.tv_sleep);
        mGradeTv = findViewById(R.id.tv_grade);
        mStartStatusTv = findViewById(R.id.tv_start_status);
        mEndStatusTv = findViewById(R.id.tv_end_status);
        mSleepStatusTv = findViewById(R.id.tv_sleep_status);
        mGradeStatusTv = findViewById(R.id.tv_grade_status);
        mDreamNumberTv = findViewById(R.id.tv_dream_number);
    }

    @Override
    public void initListener() {
        mTitleBar.setLeftIconClickListener(new TitleBar.LeftIconClickListener() {
            @Override
            public void leftIconClick() {
                finish();
            }
        });
        mTitleBar.setRightIconClickListener(new TitleBar.RightIconClickListener() {
            @Override
            public void rightIconClick() {
                checkPermission();
//                ShareSdkUtil.QQFriendsShareUrl(ReportDetailActivity.this,null,null,null);
            }
        });
    }

    private void checkPermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        ShareSdkUtil.QQFriendsShareImage(ReportDetailActivity.this,null,saveImage(shotScrollView(mNestedScrollView)),null);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        onBackPressed();
                    }
                })
                .start();
    }

    /**
     *  对ScrollView进行截图
     * @param scrollView
     * @return
     */
    public Bitmap shotScrollView(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.report_detail_bg_color));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap
     * @return
     */
    private String saveImage(Bitmap bitmap) {

        File path = getCacheDir();

        String fileName = "shareImage.png";

        File file = new File(path, fileName);

        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    @Override
    public void initData() {
//        List<QualityBean> qualityBeans = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            QualityBean qualityBean = new QualityBean();
//            qualityBean.setType(i % 4);
//            qualityBean.setGrade(10 + (i % 5) * 20);
//            qualityBeans.add(qualityBean);
//        }
        mMediaPlayer = new MediaPlayer();
        if (reportBean != null) {
            mSleepQualityView.setData(reportBean.getQualityBeans());
            mDateTv.setText(DateUtil.formatOne(reportBean.getStartTime()));
            mStartTimeTv.setText(DateUtil.formatTwo(reportBean.getStartTime()));
            mEndTimeTv.setText(DateUtil.formatTwo(reportBean.getEndTime()));
            mStartTv.setText(DateUtil.formatTwo(reportBean.getStartTime()));
            mEndTv.setText(DateUtil.formatTwo(reportBean.getEndTime()));
            mSleepTv.setText(DateUtil.getDistanceTime(reportBean.getStartTime(), reportBean.getEndTime()));
            mGradeTv.setText(String.valueOf(reportBean.getGrade()));
            setAllStatus();
        }


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReportDetailAdapter(this);
        mAdapter.addItemClickListener(this);
        if (mItems == null) {
            mItems = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                DreamBean dreamBean = new DreamBean();
//                mItems.add(dreamBean);
//            }
        }
        if (mItems != null && mItems.size() > 0) {
            ViewUtil.setViewVisible(mFoldTitleLl);
        } else {
            ViewUtil.setViewGone(mFoldTitleLl);
        }
        mAdapter.setData(mItems);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getDreamData(reportBean.getStartTime(), reportBean.getEndTime());
    }

    private void setAllStatus() {
        switch (startStatus(reportBean.getStartTime())) {
            case Constant.SleepStatus.SLEEP_START_STATUS_EARLY:
                mStartStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_1_text_color));
                mStartStatusTv.setText(getResources().getString(R.string.report_detail_status_2));
                break;
            case Constant.SleepStatus.SLEEP_START_STATUS_NORMAL:
                mStartStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_text_color));
                mStartStatusTv.setText(getResources().getString(R.string.report_detail_status_1));
                break;
            case Constant.SleepStatus.SLEEP_START_STATUS_LATE:
                mStartStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_2_text_color));
                mStartStatusTv.setText(getResources().getString(R.string.report_detail_status_3));
                break;
            case Constant.SleepStatus.SLEEP_START_STATUS_ERROR:
                mStartStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_3_text_color));
                mStartStatusTv.setText(getResources().getString(R.string.report_detail_status_8));
                break;
            default:
                break;

        }

        switch (endStatus(reportBean.getEndTime())) {
            case Constant.SleepStatus.SLEEP_END_STATUS_EARLY:
                mEndStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_1_text_color));
                mEndStatusTv.setText(getResources().getString(R.string.report_detail_status_2));
                break;
            case Constant.SleepStatus.SLEEP_END_STATUS_NORMAL:
                mEndStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_text_color));
                mEndStatusTv.setText(getResources().getString(R.string.report_detail_status_1));
                break;
            case Constant.SleepStatus.SLEEP_END_STATUS_LATE:
                mEndStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_2_text_color));
                mEndStatusTv.setText(getResources().getString(R.string.report_detail_status_3));
                break;
            case Constant.SleepStatus.SLEEP_END_STATUS_ERROR:
                mEndStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_3_text_color));
                mEndStatusTv.setText(getResources().getString(R.string.report_detail_status_8));
                break;
            default:
                break;

        }

        switch (sleepTimeStatus(reportBean.getEndTime(), reportBean.getStartTime())) {
            case Constant.SleepStatus.SLEEP_TIME_STATUS_LOW:
                mSleepStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_1_text_color));
                mSleepStatusTv.setText(getResources().getString(R.string.report_detail_status_4));
                break;
            case Constant.SleepStatus.SLEEP_TIME_STATUS_NORMAL:
                mSleepStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_text_color));
                mSleepStatusTv.setText(getResources().getString(R.string.report_detail_status_1));
                break;
            case Constant.SleepStatus.SLEEP_TIME_STATUS_HIGH:
                mSleepStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_2_text_color));
                mSleepStatusTv.setText(getResources().getString(R.string.report_detail_status_5));
                break;
            case Constant.SleepStatus.SLEEP_TIME_STATUS_ERROR:
                mSleepStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_3_text_color));
                mSleepStatusTv.setText(getResources().getString(R.string.report_detail_status_8));
                break;
            default:
                break;

        }

        switch (gradeStatus(reportBean.getGrade())) {
            case Constant.SleepStatus.SLEEP_GRADE_STATUS_LOW:
                mGradeStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_1_text_color));
                mGradeStatusTv.setText(getResources().getString(R.string.report_detail_status_6));
                break;
            case Constant.SleepStatus.SLEEP_GRADE_STATUS_NORMAL:
                mGradeStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_text_color));
                mGradeStatusTv.setText(getResources().getString(R.string.report_detail_status_1));
                break;
            case Constant.SleepStatus.SLEEP_GRADE_STATUS_HIGH:
                mGradeStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_2_text_color));
                mGradeStatusTv.setText(getResources().getString(R.string.report_detail_status_7));
                break;
            case Constant.SleepStatus.SLEEP_GRADE_STATUS_ERROR:
                mGradeStatusTv.setTextColor(getResources().getColor(R.color.report_detail_status_3_text_color));
                mGradeStatusTv.setText(getResources().getString(R.string.report_detail_status_8));
                break;
            default:
                break;

        }
    }

    private int startStatus(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String h = sdf.format(timeStamp);
        int hour = Integer.valueOf(h);
        if (hour < Constant.Hour.HOUR_TWENTY && hour > Constant.Hour.HOUR_TWELVE) {
            return Constant.SleepStatus.SLEEP_START_STATUS_EARLY;
        } else if (hour >= Constant.Hour.HOUR_TWENTY && hour <= Constant.Hour.HOUR_TWENTY_THREE) {
            return Constant.SleepStatus.SLEEP_START_STATUS_NORMAL;
        } else if (hour > Constant.Hour.HOUR_TWENTY_THREE || (hour >= Constant.Hour.HOUR_ZERO && hour <= Constant.Hour.HOUR_SIX)) {
            return Constant.SleepStatus.SLEEP_START_STATUS_LATE;
        } else {
            return Constant.SleepStatus.SLEEP_START_STATUS_ERROR;
        }
    }

    private int endStatus(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String h = sdf.format(timeStamp);
        int hour = Integer.valueOf(h);
        if (hour < Constant.Hour.HOUR_SIX && hour > Constant.Hour.HOUR_TWO) {
            return Constant.SleepStatus.SLEEP_END_STATUS_EARLY;
        } else if (hour >= Constant.Hour.HOUR_SIX && hour <= Constant.Hour.HOUR_NINE) {
            return Constant.SleepStatus.SLEEP_END_STATUS_NORMAL;
        } else if (hour > Constant.Hour.HOUR_NINE && hour < Constant.Hour.HOUR_SEVENTEEN) {
            return Constant.SleepStatus.SLEEP_END_STATUS_LATE;
        } else {
            return Constant.SleepStatus.SLEEP_END_STATUS_ERROR;
        }
    }

    private int sleepTimeStatus(long endTime, long startTime) {
        int hour = (int) ((endTime - startTime) / Constant.HOUR);
        if (hour < Constant.Hour.HOUR_SIX && hour > Constant.Hour.HOUR_ZERO) {
            return Constant.SleepStatus.SLEEP_TIME_STATUS_LOW;
        } else if (hour >= Constant.Hour.HOUR_SIX && hour <= Constant.Hour.HOUR_TWELVE) {
            return Constant.SleepStatus.SLEEP_TIME_STATUS_NORMAL;
        } else if (hour > Constant.Hour.HOUR_TWELVE && hour < Constant.Hour.HOUR_TWENTY) {
            return Constant.SleepStatus.SLEEP_TIME_STATUS_HIGH;
        } else {
            return Constant.SleepStatus.SLEEP_TIME_STATUS_ERROR;
        }
    }

    private int gradeStatus(int grade) {
        if (grade < 65) {
            return Constant.SleepStatus.SLEEP_GRADE_STATUS_LOW;
        } else if (grade <= 85) {
            return Constant.SleepStatus.SLEEP_GRADE_STATUS_NORMAL;
        } else if (grade <= 100) {
            return Constant.SleepStatus.SLEEP_GRADE_STATUS_HIGH;
        } else {
            return Constant.SleepStatus.SLEEP_GRADE_STATUS_ERROR;
        }
    }

    @Override
    public void getDreamDataSuccess(List<DreamBean> dreamBeanList) {
        mItems.addAll(dreamBeanList);
        if (mItems != null && mItems.size() > 0) {
            ViewUtil.setViewVisible(mFoldTitleLl);
            mDreamNumberTv.setText(mItems.size() + "段梦话");
        } else {
            ViewUtil.setViewGone(mFoldTitleLl);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseRecyclerHolder holder, int position) {
        if (holder instanceof ReportDetailHolder) {
            if (mItems.get(position).getItemType() == Constant.ItemType.RECORD_DREAM) {
                try {
                    DreamBean dreamBean = (DreamBean) mItems.get(position);
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                        mMediaPlayer.reset();
                    }
                    mMediaPlayer.setDataSource(dreamBean.getPath());
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                for (int i = 0; i < mItems.size(); i++) {
                    if (mItems.get(i).getItemType() == Constant.ItemType.RECORD_DREAM) {
                        DreamBean dreamBean = (DreamBean) mItems.get(i);
                        if (i == position) {
                            dreamBean.setPlaying(true);
                        } else {
                            dreamBean.setPlaying(false);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
