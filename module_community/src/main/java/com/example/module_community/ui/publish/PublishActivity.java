package com.example.module_community.ui.publish;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.view.ImageSelectView;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_community.R;
import com.example.module_community.contract.PublishContract;
import com.example.module_community.presenter.PublishPresenter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.ui.MatisseActivity;

/**
 * @author tianhuaye
 */
public class PublishActivity extends BaseActivity<PublishContract.View, PublishPresenter<PublishContract.View>> implements PublishContract.View {

    private final int REQUEST_CODE_CHOOSE = 1;

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"Manifest.permission.READ_EXTERNAL_STORAGE"}, 2);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"Manifest.permission.WRITE_EXTERNAL_STORAGE"}, 3);
        }
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

    private void selectImage(int size){
        Matisse.from(this)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .maxSelectable(size)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }
}
