package com.example.module_habit.ui.alarm;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.lib_common.base.fragment.BaseFragment;
import com.example.lib_common.base.transformer.GalleryTransformer;
import com.example.lib_common.base.view.WrapContentHeightViewPager;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.util.DateUtil;
import com.example.module_habit.BuildConfig;
import com.example.module_habit.R;
import com.example.module_habit.contract.AlarmContract;
import com.example.module_habit.presenter.alarm.AlarmPresenter;
import com.example.module_habit.ui.alarm.adapter.AlarmPagerAdapter;
import com.example.module_habit.view.AlarmCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2018/11/14 13:01
 * @description:
 */
public class AlarmFragment extends BaseFragment<AlarmContract.View, AlarmPresenter<AlarmContract.View>> implements AlarmContract.View, ViewPager.OnPageChangeListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    private WrapContentHeightViewPager mViewPager;
    private AlarmPagerAdapter mAlarmPagerAdapter;
    private LinearLayout llPointGroup;
    private ImageView ivRedPoint;
    private LinearLayout mSmallType1Ll;
    private int widthDpi;
    private int marginLeft;
    private List<AlarmCardView> alarmCardViewList;
    private List<Alarm> alarmList;

    @Override
    protected AlarmPresenter<AlarmContract.View> createPresenter() {
        return new AlarmPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        mViewPager = view.findViewById(R.id.view_pager);
        llPointGroup = view.findViewById(R.id.ll_point_group);
        ivRedPoint = view.findViewById(R.id.iv_red_point);
        mSmallType1Ll = view.findViewById(R.id.ll_small_sleep_type_1);

        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(this);
        widthDpi = getResources().getDimensionPixelOffset(R.dimen.alarm_guide_point_width);
        for (int i = 0; i < 3; i++) {
            ImageView point = new ImageView(getContext());
            point.setBackgroundResource(R.drawable.common_shape_guide_point_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthDpi, widthDpi);
            if (i != 0) {
                params.leftMargin = widthDpi * 2;
            }
            point.setLayoutParams(params);
            llPointGroup.addView(point);
        }


        mViewPager.setPageTransformer(true, new GalleryTransformer());
        if (alarmCardViewList == null) {
            alarmCardViewList = new ArrayList<>();
        }



        return view;
    }

    @Override
    public void initListener() {
        mViewPager.addOnPageChangeListener(this);
        mSmallType1Ll.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mPresenter.getBaseAlarm();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.RequestAndResultCode.ALARM_RESULT_OK) {
            switch (requestCode) {
                case Constant.RequestAndResultCode.ALARM_REQUEST:
                    int id = data.getIntExtra("id", 0);
                    int hour = data.getIntExtra("hour", 0);
                    int minute = data.getIntExtra("minute", 0);
                    String msg = data.getStringExtra("msg");
                    String ringPath = data.getStringExtra("ringPath");

                    if (alarmCardViewList != null && alarmCardViewList.size() == 3 && alarmList.size() == 3) {
                        if (id == Constant.Alarm.ALARM_ID_ONE) {
                            alarmCardViewList.get(0).setLeftTopText(DateUtil.timeToStr(hour, minute));
                            alarmCardViewList.get(0).setLeftBottomText(msg);
                            alarmCardViewList.get(0).setSwitchButtonOpen(true);
                            alarmList.get(0).setHour(hour);
                            alarmList.get(0).setMinute(minute);
                            alarmList.get(0).setMsg(msg);
                            alarmList.get(0).setRingPath(ringPath);
                            if (mAlarmPagerAdapter != null || mViewPager != null) {
                                mViewPager.setAdapter(mAlarmPagerAdapter);
                                mViewPager.setCurrentItem(0);
                            }

                        } else if (id == Constant.Alarm.ALARM_ID_TWO) {
                            alarmCardViewList.get(1).setLeftTopText(DateUtil.timeToStr(hour, minute));
                            alarmCardViewList.get(1).setLeftBottomText(msg);
                            alarmCardViewList.get(1).setSwitchButtonOpen(true);
                            alarmList.get(1).setHour(hour);
                            alarmList.get(1).setMinute(minute);
                            alarmList.get(1).setMsg(msg);
                            alarmList.get(1).setRingPath(ringPath);
                            if (mAlarmPagerAdapter != null || mViewPager != null) {
                                mViewPager.setAdapter(mAlarmPagerAdapter);
                                mViewPager.setCurrentItem(1);

                            }
                        } else if (id == Constant.Alarm.ALARM_ID_THREE) {
                            alarmCardViewList.get(2).setLeftTopText(DateUtil.timeToStr(hour, minute));
                            alarmCardViewList.get(2).setLeftBottomText(msg);
                            alarmCardViewList.get(2).setSwitchButtonOpen(true);
                            alarmList.get(2).setHour(hour);
                            alarmList.get(2).setMinute(minute);
                            alarmList.get(2).setMsg(msg);
                            alarmList.get(2).setRingPath(ringPath);
                            if (mAlarmPagerAdapter != null || mViewPager != null) {
                                mViewPager.setAdapter(mAlarmPagerAdapter);
                                mViewPager.setCurrentItem(2);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        marginLeft = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        float leftMargin = (i + v) * marginLeft;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
        params.leftMargin = (int) leftMargin;
        ivRedPoint.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int i) {
        if (mAlarmPagerAdapter != null) {
            mAlarmPagerAdapter.setCurrenetPage(i);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_small_sleep_type_1) {
            startActivity(new Intent(getContext(), SmallSleepActivity.class));
        }
    }

    @Override
    public void showBaseAlarm(final List<Alarm> list) {
        alarmList = list;
        if (BuildConfig.DEBUG) {
            Log.e("AlarmFragment", "showBaseAlarm: " + list);
        }
        if (list == null) {
            return;
        }
        if (alarmCardViewList == null) {
            alarmCardViewList = new ArrayList<>();
        }
        alarmCardViewList.clear();
        for (int i = 0; i < list.size(); i++) {
            final Alarm alarm = list.get(i);
            AlarmCardView alarmCardView = new AlarmCardView(getContext());
            alarmCardView.setLeftTopText(DateUtil.timeToStr(alarm.getHour(), alarm.getMinute()));
            alarmCardView.setLeftTopTextColor(getContext().getResources().getColor(R.color.alarm_left_top_time_color));
            alarmCardView.setLeftTopTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.alarm_left_top_time_size));
            alarmCardView.setLeftBottomText(alarm.getMsg());
            alarmCardView.setLeftBottomTextColor(getContext().getResources().getColor(R.color.alarm_left_bottom_name));
            alarmCardView.setLeftBottomTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.alarm_left_bottom_name_size));
            alarmCardView.setSwitchButtonOpen(alarm.getOpen());

            alarmCardView.setSwitchButtonClickListener(new AlarmCardView.OnToggleChangeListener() {
                @Override
                public void toggleChange(boolean open) {
                    mPresenter.changeAlarmOpenState(alarm.getId(), open);
                }
            });
//            if (BuildConfig.DEBUG){
//                Log.e("AlarmFragment", "showBaseAlarm: "+alarm.getId());
//            }
            alarmCardView.addEditClickListener(new AlarmCardView.OnEditClickListener() {
                @Override
                public void onEditClick() {
                    Intent intent = new Intent(getContext(), AlarmActivity.class);
                    intent.putExtra(AlarmActivity.INTENT_SOURCE_PAGE, 3);
                    intent.putExtra(AlarmActivity.INTENT_CUSTOM_ID, alarm.getId());
                    intent.putExtra(AlarmActivity.INTENT_HOUR, alarm.getHour());
                    intent.putExtra(AlarmActivity.INTENT_MINUTE, alarm.getMinute());
                    intent.putExtra(AlarmActivity.INTENT_RING_PATH, alarm.getRingPath());
                    intent.putExtra(AlarmActivity.INTENT_NAME, alarm.getMsg());
                    startActivityForResult(intent, Constant.RequestAndResultCode.ALARM_REQUEST);
                }
            });
            alarmCardViewList.add(alarmCardView);
        }
        mAlarmPagerAdapter = new AlarmPagerAdapter(alarmCardViewList);
        mViewPager.setAdapter(mAlarmPagerAdapter);
    }
}
