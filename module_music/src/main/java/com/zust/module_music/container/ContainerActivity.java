package com.zust.module_music.container;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.lib_common.base.adapter.BaseFragmentPagerAdapter;
import com.zust.module_music.R;
import com.zust.module_music.ui.MusicFragment;

import java.util.ArrayList;
import java.util.List;

public class ContainerActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        List<Fragment> fragments=new ArrayList<>();
        List<String> titles=new ArrayList<>();
        fragments.add(new MusicFragment());
        titles.add("music");
        mViewPager=findViewById(R.id.view_pager);
        BaseFragmentPagerAdapter adapter=new BaseFragmentPagerAdapter(getSupportFragmentManager());
        adapter.setData(fragments,titles);
        mViewPager.setAdapter(adapter);
    }
}
