package com.example.module_habit.ui.lifestyle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.fragment.BaseFragment;
import com.example.module_habit.R;

/**
 * author: tianhuaye
 * date:   2018/11/23 11:09
 * description:
 */
public class LifestyleFragment extends BaseFragment<LifestyleContract.View,LifestylePresenter<LifestyleContract.View>> implements LifestyleContract.View {

    @Override
    protected LifestylePresenter<LifestyleContract.View> createPresenter() {
        return new LifestylePresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_lifestyle,container,false);
        return view;
    }

    @Override
    public void initData() {

    }

}
