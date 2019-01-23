package com.example.module_community.ui.publish;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.view.ImageSelectView;
import com.example.lib_common.util.StatusBarUtil;
import com.example.lib_common.util.ToastUtil;
import com.example.module_community.R;
import com.example.module_community.contract.PublishContract;
import com.example.module_community.presenter.PublishPresenter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.ui.MatisseActivity;

/**
 * @author tianhuaye
 */
public class PublishActivity extends BaseActivity<PublishContract.View, PublishPresenter<PublishContract.View>> implements PublishContract.View, ImageSelectView.AddImageClick {

    private final int REQUEST_CODE_CHOOSE = 1;
    private final int REQUEST_PERMISSION_STORAGE_READ_AND_WRITE = 2;

    private ImageSelectView mImageSelectView;

    @Override
    protected PublishPresenter<PublishContract.View> createPresenter() {
        return new PublishPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initUIParams() {
        super.initUIParams();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.community_publish_title_bar_bg), 0);
        StatusBarUtil.setStatusBarDarkMode(this);
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_publish;
    }

    @Override
    public void initView() {
        mImageSelectView = findViewById(R.id.image_select_view);


    }

    @Override
    public void initListener() {
        mImageSelectView.setImageClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mImageSelectView.notifyDataChanged(data, MatisseActivity.EXTRA_RESULT_SELECTION);
        }
    }

    private void selectImage(int size) {
        Matisse.from(this)
                .choose(MimeType.ofImage(), true)
                .countable(true)
                .showSingleMediaType(true)
                .maxSelectable(size)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onAddImageClick(int size) {
        checkPermission(size);
//        selectImage(size);
    }

    private void checkPermission(final int size) {

        AndPermission.with(this)
                .runtime()
                .permission(new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE})
                .onGranted(data -> selectImage(size))
                .onDenied(data -> refusePermission())
                .start();
    }

    private void refusePermission() {

        // 打开权限设置页
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
        ToastUtil.showShortToastMessage(this,"前往权限设置界面");

    }
}
