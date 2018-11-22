package com.example.module_habit.ui.alarm.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.module_habit.R;
import com.example.module_habit.view.AlarmCardView;

/**
 * author: tianhuaye
 * date:   2018/11/21 16:29
 * description:
 */
public class AlarmPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = new AlarmCardView(container.getContext());
        ((AlarmCardView) view).setLeftTopText("07:30");
        ((AlarmCardView) view).setLeftTopTextColor(container.getContext().getResources().getColor(R.color.alarm_left_top_time_color));
        ((AlarmCardView) view).setLeftTopTextSize(container.getContext().getResources().getDimensionPixelSize(R.dimen.alarm_left_top_time_size));
        ((AlarmCardView) view).setLeftBottomText(R.string.habit_alarm_number_1);
        ((AlarmCardView) view).setLeftBottomTextColor(container.getContext().getResources().getColor(R.color.alarm_left_bottom_name));
        ((AlarmCardView) view).setLeftBottomTextSize(container.getContext().getResources().getDimensionPixelSize(R.dimen.alarm_left_bottom_name_size));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
