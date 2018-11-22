package com.example.module_habit.ui.alarm;

import android.os.Build;
import android.support.v4.view.ViewPager;
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
import com.example.module_habit.R;
import com.example.module_habit.contract.AlarmContract;
import com.example.module_habit.presenter.alarm.AlarmPresenter;
import com.example.module_habit.ui.alarm.adapter.AlarmPagerAdapter;

/**
 * author: tianhuaye
 * date:   2018/11/14 13:01
 * description:
 */
public class AlarmFragment extends BaseFragment<AlarmContract.View, AlarmPresenter<AlarmContract.View>> implements ViewPager.OnPageChangeListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    private WrapContentHeightViewPager mViewPager;
    private AlarmPagerAdapter mAlarmPagerAdapter;
    private LinearLayout llPointGroup;
    private ImageView ivRedPoint;
    private int widthDpi;
    private int marginLeft;

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

        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(this);
        widthDpi = getResources().getDimensionPixelOffset(R.dimen.alarm_guide_point_width);
        for (int i = 0; i < 3; i++) {
            ImageView point = new ImageView(getContext());
            point.setBackgroundResource(R.drawable.common_shape_guide_point_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthDpi, widthDpi);
            if (i != 0) {
                params.leftMargin = widthDpi*2;
            }
            point.setLayoutParams(params);
            llPointGroup.addView(point);
        }

        mViewPager.setPageTransformer(true, new GalleryTransformer());
        mAlarmPagerAdapter = new AlarmPagerAdapter();
        mViewPager.setAdapter(mAlarmPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String message, String code) {

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

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
