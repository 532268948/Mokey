package com.example.module_community.container;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.lib_common.base.adapter.BaseFragmentPagerAdapter;
import com.example.module_community.R;
import com.example.module_community.ui.CommunityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianhuaye
 */
public class ContainerActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        List<Fragment> fragments=new ArrayList<>();
        List<String> titles=new ArrayList<>();
        fragments.add(new CommunityFragment());
        titles.add("community");
        mViewPager=findViewById(R.id.view_pager);
        BaseFragmentPagerAdapter adapter=new BaseFragmentPagerAdapter(getSupportFragmentManager());
        adapter.setData(fragments,titles);
        mViewPager.setAdapter(adapter);
    }
}
