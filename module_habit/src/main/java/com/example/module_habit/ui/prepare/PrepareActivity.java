package com.example.module_habit.ui.prepare;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.inter.OnItemClickListener;
import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.bean.BaseItem;
import com.example.lib_common.bean.SleepPrepareItem;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.util.DateUtil;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_habit.BuildConfig;
import com.example.module_habit.R;
import com.example.module_habit.bean.PrepareBean;
import com.example.module_habit.ui.prepare.adapter.PrepareAdapter;
import com.example.module_habit.view.SleepPrepareView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 53226
 */
public class PrepareActivity extends BaseActivity<PrepareContract.View, PreparePresenter<PrepareContract.View>> implements PrepareContract.View, OnItemClickListener, PrepareAdapter.OnSelectChangeListener {

    private TitleBar mTitleBar;
    private RecyclerView mRecyclerView;
    private SleepPrepareView mSleepPrepareView;
    private PrepareAdapter mAdapter;
    private PrepareTipDialog mTipDialog;
    private List<BaseItem> mItems;
    private int[] images = {R.drawable.habit_prepare_tip_1, R.drawable.habit_prepare_tip_2, R.drawable.habit_prepare_tip_3, R.drawable.habit_prepare_tip_4, R.drawable.habit_prepare_tip_5};
    private int[] bigImages = {R.drawable.habit_prepare_tip_big_1, R.drawable.habit_prepare_tip_big_2, R.drawable.habit_prepare_tip_big_3, R.drawable.habit_prepare_tip_big_4, R.drawable.habit_prepare_tip_big_5};
    private String[] titles;
    private String[] shortTips;
    private String[] tips;

    @Override
    protected PreparePresenter<PrepareContract.View> createPresenter() {
        return new PreparePresenter<>();
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_prepare;
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.main_status_bar_color), 0);
        mTitleBar = findViewById(R.id.title_bar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mSleepPrepareView = findViewById(R.id.sleep_prepare_view);
        mAdapter = new PrepareAdapter(this, mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mTitleBar.setLeftIconClickListener(new TitleBar.LeftIconClickListener() {
            @Override
            public void leftIconClick() {
                finish();
            }
        });
        mTitleBar.setRightTextClickListener(new TitleBar.RightTextClickListener() {
            @Override
            public void rightTextClick() {
                showDialog(null);
                mPresenter.setSleepPrepareAlarm(mSleepPrepareView.getData());
            }
        });
        mAdapter.addItemClickListener(this);
        mAdapter.setSelectChangeListener(this);
    }

    @Override
    public void initData() {
        titles = getResources().getStringArray(R.array.titles);
        shortTips = getResources().getStringArray(R.array.shortTips);
        tips = getResources().getStringArray(R.array.tips);
        if (mItems == null) {
            mItems = new ArrayList<>();
            SleepPrepareItem sleepPrepareItem;
            for (int i = 0; i < titles.length; i++) {
                sleepPrepareItem = new SleepPrepareItem();
                sleepPrepareItem.setId(i);
                sleepPrepareItem.setItemType(Constant.ItemType.HABIT_SLEEP_PREPARE);
                sleepPrepareItem.setImageResId(images[i]);
                sleepPrepareItem.setBigImageResId(bigImages[i]);
                sleepPrepareItem.setTitle(titles[i]);
                sleepPrepareItem.setShortTip(shortTips[i]);
                sleepPrepareItem.setMessage(tips[i]);
                mItems.add(sleepPrepareItem);
            }
        }
        mAdapter.setData(mItems);
        mPresenter.getSleepPrepareAlarm();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(BaseRecyclerHolder holder, int position) {
        if (mItems != null && position >= 0 && position < mItems.size() && mItems.get(position) != null) {
            if (mItems.get(position).getItemType() == Constant.ItemType.HABIT_SLEEP_PREPARE) {
                SleepPrepareItem sleepPrepareItem = (SleepPrepareItem) mItems.get(position);
                showTipDialog(sleepPrepareItem.getBigImageResId(), sleepPrepareItem.getTitle(), sleepPrepareItem.getMessage());
            }
        }
    }

    private void showTipDialog(int imageResId, String title, String tip) {
        if (mTipDialog == null) {
            mTipDialog = new PrepareTipDialog();
            WindowManager wm = (WindowManager) this
                    .getSystemService(Context.WINDOW_SERVICE);
            mTipDialog.setSize((int) (wm.getDefaultDisplay().getWidth() * 0.7), 0);
        }
        mTipDialog.setMessage(imageResId, title, tip);
        mTipDialog.show(getSupportFragmentManager());
    }

    @Override
    public void onChange() {
        List<PrepareBean> list = new ArrayList<>();
        for (BaseItem baseItem : mItems) {
            if (baseItem.getItemType() == Constant.ItemType.HABIT_SLEEP_PREPARE) {
                SleepPrepareItem prepareItem = (SleepPrepareItem) baseItem;
                if (prepareItem.getChecked()) {
                    PrepareBean prepareBean = new PrepareBean();
                    prepareBean.setSleepPrepareItem((SleepPrepareItem) baseItem);
                    list.add(prepareBean);
                }
            }
        }
        mSleepPrepareView.setData(list);
    }

    @Override
    public void setPrepareAlarmList(List<Alarm> alarmList) {
        int k = 0;
        for (Alarm alarm : alarmList) {
            for (BaseItem baseItem : mItems) {
                if (baseItem.getItemType() == Constant.ItemType.HABIT_SLEEP_PREPARE) {
                    SleepPrepareItem prepareItem = (SleepPrepareItem) baseItem;
                    if (alarm.getMsg().equals(prepareItem.getTitle())) {
                        prepareItem.setChecked(true);
                        k=k+1;
                        prepareItem.setCheckNum(k);
                    }
                }
            }
        }

        mAdapter.notifyDataSetChanged();

        List<PrepareBean> list = new ArrayList<>();
        for (BaseItem baseItem : mItems) {
            if (baseItem.getItemType() == Constant.ItemType.HABIT_SLEEP_PREPARE) {
                SleepPrepareItem prepareItem = (SleepPrepareItem) baseItem;
                if (prepareItem.getChecked()) {
                    PrepareBean prepareBean = new PrepareBean();
                    for (int i=alarmList.size()-1;i>=0;i--){
                        if (prepareItem.getTitle().equals(alarmList.get(i).getMsg())){
//                            int destHour=0;
//                            int destMinute=0;
//                            if (alarmList.get(0).getMinute()+30>=60){
//                                destHour=alarmList.get(0).getHour()+1;
//                                destMinute=alarmList.get(0).getMinute()+30-60;
//                            }
                            if (alarmList.size()==1){
                                prepareBean.setTime(30);
                            }else if (alarmList.size()==2){
                                if (i == 1) {
                                    prepareBean.setTime(30f);
                                }else if (i==0){
                                    prepareBean.setTime(DateUtil.getMinuteLeft(alarmList.get(0).getHour(),alarmList.get(0).getMinute(),alarmList.get(1).getHour(),alarmList.get(1).getMinute()));
                                }

                            }else if (alarmList.size()==3){
                                if (i==2){
                                    prepareBean.setTime(30);
                                }else if (i==1){
                                    prepareBean.setTime(DateUtil.getMinuteLeft(alarmList.get(0).getHour(),alarmList.get(0).getMinute(),alarmList.get(2).getHour(),alarmList.get(2).getMinute()));
                                }else if (i==0){
                                    prepareBean.setTime(DateUtil.getMinuteLeft(alarmList.get(0).getHour(),alarmList.get(0).getMinute(),alarmList.get(1).getHour(),alarmList.get(1).getMinute()));
                                }
                            }
                        }
                    }

                    prepareBean.setSleepPrepareItem((SleepPrepareItem) baseItem);
                    list.add(prepareBean);
                }
            }
        }
        if (BuildConfig.DEBUG){
            Log.e("PrepareActivity", "setPrepareAlarmList: "+list);
        }
        mSleepPrepareView.setData(list);
    }

    @Override
    public void setAlarmSuccess() {
        dismissDialog();
        setResult(Constant.RequestAndResultCode.ACTIVITY_PREAPRE_RESULT_OK);
        finish();
    }
}
