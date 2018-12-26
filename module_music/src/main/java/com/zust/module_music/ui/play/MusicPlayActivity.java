package com.zust.module_music.ui.play;

import android.view.View;

import com.example.lib_common.base.activity.BaseActivity;
import com.zust.module_music.R;
import com.zust.module_music.contract.play.MusicPlayContract;
import com.zust.module_music.presenter.play.MusicPlayPresenter;

/**
 * @author 叶天华
 */
public class MusicPlayActivity extends BaseActivity<MusicPlayContract.View,MusicPlayPresenter<MusicPlayContract.View>> implements MusicPlayContract.View{


    @Override
    protected MusicPlayPresenter<MusicPlayContract.View> createPresenter() {
        return new MusicPlayPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_music_paly;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
