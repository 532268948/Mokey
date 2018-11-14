package com.example.module_habit.ui.alarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.fragment.BaseFragment;
import com.example.module_habit.R;
import com.example.module_habit.contract.AlarmContract;
import com.example.module_habit.presenter.alarm.AlarmPresenter;

/**
 * author: tianhuaye
 * date:   2018/11/14 13:01
 * description:
 */
public class AlarmFragment extends BaseFragment<AlarmContract.View,AlarmPresenter<AlarmContract.View>> {
    @Override
    protected AlarmPresenter<AlarmContract.View> createPresenter() {
        return new AlarmPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_alarm,container,false);

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
}
