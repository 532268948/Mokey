package com.zust.module_music.ui.play;

import android.view.View;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.util.StatusBarUtil;
import com.zust.module_music.R;
import com.zust.module_music.contract.play.StoryPlayContract;
import com.zust.module_music.presenter.play.StoryPlayPresenter;

/**
 * @author 53226
 */
public class StoryPlayActivity extends BaseActivity<StoryPlayContract.View, StoryPlayPresenter<StoryPlayContract.View>> implements StoryPlayContract.View {

    @Override
    protected StoryPlayPresenter<StoryPlayContract.View> createPresenter() {
        return new StoryPlayPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_story_play;
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.music_story_play_status_bar_color), 0);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
