package com.example.tianhuaye.monkey.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.activity.BaseBottomTabActivity;
import com.example.lib_common.base.bean.response.LoginItem;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.User;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_community.ui.CommunityFragment;
import com.example.module_habit.ui.HabitFragment;
import com.example.module_report.ui.ReportFragment;
import com.example.module_user.ui.login.LoginActivity;
import com.example.tianhuaye.monkey.R;
import com.example.tianhuaye.monkey.contract.MainContract;
import com.example.tianhuaye.monkey.presenter.MainPresenter;
import com.zust.module_music.ui.MusicFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 53226
 */
public class MainActivity extends BaseBottomTabActivity<MainContract.View, MainPresenter<MainContract.View>> implements MainContract.View {

    private DrawerLayout mDrawerLayout;
    private CircleImageView mHeadIv;
    private NavigationView mNavigationView;

    //侧滑栏控件

    private CircleImageView mLeftHeadIv;
    private TextView mLeftNicknameTv;
    private ImageView mLeftGenderIv;

    @Override
    public void initUIParams() {
        super.initUIParams();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.main_status_bar_color), 0);
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mViewPager = findViewById(R.id.view_pager);
        mTabView = findViewById(R.id.bottom_navigation_view);
        mHeadIv = findViewById(R.id.floating_head_view);

        mNavigationView = findViewById(R.id.nav_view);
        View view = mNavigationView.getHeaderView(0);

        mLeftHeadIv = view.findViewById(R.id.iv_left_head);
        mLeftNicknameTv = view.findViewById(R.id.tv_left_nickname);
        mLeftGenderIv = view.findViewById(R.id.iv_left_gender);
    }

    @Override
    public void initListener() {
        mHeadIv.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(this);
        mTabView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        //未登录
        if (TextUtils.isEmpty(Constant.TOKEN)) {
            Glide.with(this).load(R.drawable.main_head_default).into(mHeadIv);
        } else { //登录
            DBManager.getInstance(this).getUserDB().queryWhereUser(Constant.USER_ID, new DbOperateListener.OnQuerySingleListener() {
                @Override
                public void onQuerySingleListener(Object entry) {
                    if (entry != null) {
                        User user = (User) entry;
                        if (!TextUtils.isEmpty(user.getHead())) {
                            Glide.with(MainActivity.this).load(Constant.BASE_URL + user.getHead()).into(mHeadIv);
                            Glide.with(MainActivity.this).load(Constant.BASE_URL + user.getHead()).into(mLeftHeadIv);
                        }
                        if (!TextUtils.isEmpty(user.getNickname())) {
                            mLeftNicknameTv.setText(user.getNickname());
                        }
                        Glide.with(MainActivity.this).load(user.getGender() == 0 ? R.drawable.main_left_male : R.drawable.main_left_female).into(mLeftGenderIv);
                    }
                }
            });
        }

    }

    @Override
    protected void addFragment() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new CommunityFragment());
        fragmentList.add(new MusicFragment());
        fragmentList.add(new HabitFragment());
        fragmentList.add(new ReportFragment());
        mAdapter = new PlayFragmentAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RequestAndResultCode.MAIN_REQUEST) {
            switch (resultCode) {
                case Constant.RequestAndResultCode.LOGIN_RESULT_OK:
                    setUserInfo((LoginItem) data.getSerializableExtra("user"));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        this.menuItem = menuItem;
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_dashboard:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_habit:
                mViewPager.setCurrentItem(2);
                return true;
            case R.id.navigation_person:
                mViewPager.setCurrentItem(3);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            mTabView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = mTabView.getMenu().getItem(i);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floating_head_view) {
            if (TextUtils.isEmpty(Constant.TOKEN)) {
                ARouter.getInstance().build(Constant.Activity.ACTIVITY_LOGIN).navigation(MainActivity.this, Constant.RequestAndResultCode.MAIN_REQUEST);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
    }

    @Override
    public void gotoLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, Constant.RequestAndResultCode.MAIN_REQUEST);
    }

    private void setUserInfo(LoginItem userInfo) {
        Glide.with(MainActivity.this).load(Constant.BASE_URL + userInfo.getHead()).into(mHeadIv);
    }
}
