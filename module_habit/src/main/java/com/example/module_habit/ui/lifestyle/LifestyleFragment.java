package com.example.module_habit.ui.lifestyle;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.fragment.BaseFragment;
import com.example.module_habit.R;
import com.example.module_habit.ui.prepare.PrepareActivity;
import com.example.module_habit.view.AlarmCardView;

/**
 * author: tianhuaye
 * date:   2018/11/23 11:09
 * description:
 */
public class LifestyleFragment extends BaseFragment<LifestyleContract.View, LifestylePresenter<LifestyleContract.View>> implements LifestyleContract.View {

    private AlarmCardView mPrepareAv;
    private AlarmCardView mRemindAv;

    @Override
    protected LifestylePresenter<LifestyleContract.View> createPresenter() {
        return new LifestylePresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_lifestyle, container, false);
        mPrepareAv = view.findViewById(R.id.av_sleep_prepare);
        mRemindAv = view.findViewById(R.id.av_sleep_remind);

        return view;
    }

    @Override
    public void initListener() {
        mPrepareAv.addEditClickListener(new AlarmCardView.OnEditClickListener() {
            @Override
            public void onEditClick() {
                startActivity(new Intent(getContext(), PrepareActivity.class));
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}
