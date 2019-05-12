package com.example.module_habit.ui.alarm.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.module_habit.BuildConfig;
import com.example.module_habit.view.AlarmCardView;

import java.util.List;

/**
 * author: tianhuaye
 * date:   2018/11/21 16:29
 * description:
 */
public class AlarmPagerAdapter extends PagerAdapter {

    private int currentPage = -1;
    private OnItemClickListener onItemClickListener;
    private List<AlarmCardView> list;

    public AlarmPagerAdapter(List<AlarmCardView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = list.get(position);
        view.setTag(position);
//        ((AlarmCardView) view).setLeftTopText("07:30");
//        ((AlarmCardView) view).setLeftTopTextColor(container.getContext().getResources().getColor(R.color.alarm_left_top_time_color));
//        ((AlarmCardView) view).setLeftTopTextSize(container.getContext().getResources().getDimensionPixelSize(R.dimen.alarm_left_top_time_size));
//        ((AlarmCardView) view).setLeftBottomText(R.string.habit_alarm_number_1);
//        ((AlarmCardView) view).setLeftBottomTextColor(container.getContext().getResources().getColor(R.color.alarm_left_bottom_name));
//        ((AlarmCardView) view).setLeftBottomTextSize(container.getContext().getResources().getDimensionPixelSize(R.dimen.alarm_left_bottom_name_size));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        View view = (View) object;
        if (BuildConfig.DEBUG){
            Log.e("AlarmPagerAdapter", "getItemPosition: "+currentPage+" "+view.getTag());
        }
        if (currentPage == (Integer) view.getTag()) {
            return POSITION_NONE;
        } else {
            return POSITION_UNCHANGED;
        }
    }

    public void setCurrenetPage(int page) {
        this.currentPage = page;
    }

    public void addItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
