package com.example.module_habit.ui.prepare;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.bean.BaseItem;
import com.example.lib_common.base.bean.SleepPrepareItem;
import com.example.lib_common.base.inter.OnItemClickListener;
import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_habit.R;
import com.example.module_habit.bean.PrepareBean;
import com.example.module_habit.ui.prepare.adapter.PrepareAdapter;
import com.example.module_habit.view.SleepPrepareView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 53226
 */
public class PrepareActivity extends BaseActivity<PrepareContract.View, PreparePresenter<PrepareContract.View>> implements OnItemClickListener, BaseView, PrepareAdapter.OnSelectChangeListener {

    private TitleBar mTitleBar;
    private RecyclerView mRecyclerView;
    private SleepPrepareView mSleepPrepareView;
    private PrepareAdapter mAdapter;
    private PrepareTipDialog mTipDialog;
    private List<BaseItem> mItems;
    private int[] images = {R.drawable.habit_prepare_tip_1, R.drawable.habit_prepare_tip_2, R.drawable.habit_prepare_tip_3, R.drawable.habit_prepare_tip_4, R.drawable.habit_prepare_tip_5};
    private int[] bigImages = {R.drawable.habit_prepare_tip_big_1, R.drawable.habit_prepare_tip_big_2, R.drawable.habit_prepare_tip_big_3, R.drawable.habit_prepare_tip_big_4, R.drawable.habit_prepare_tip_big_5};
    private int[] titles = {R.string.prepare_tip_name_1, R.string.prepare_tip_name_2, R.string.prepare_tip_name_3, R.string.prepare_tip_name_4, R.string.prepare_tip_name_5};
    private int[] shortTips = {R.string.prepare_short_tip_1, R.string.prepare_short_tip_2, R.string.prepare_short_tip_3, R.string.prepare_short_tip_4, R.string.prepare_short_tip_5};
    private int[] tips = {R.string.prepare_tip_1, R.string.prepare_tip_2, R.string.prepare_tip_3, R.string.prepare_tip_4, R.string.prepare_tip_5};

    @Override
    protected PreparePresenter<PrepareContract.View> createPresenter() {
        return new PreparePresenter<>();
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_prepare;
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.main_status_bar_color), 0);
        mTitleBar = findViewById(R.id.title_bar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mSleepPrepareView = findViewById(R.id.sleep_prepare_view);
        mAdapter = new PrepareAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mAdapter.addItemClickListener(this);
        mAdapter.setSelectChangeListener(this);
    }

    @Override
    public void initData() {
        if (mItems == null) {
            mItems = new ArrayList<>();
            SleepPrepareItem sleepPrepareItem;
            for (int i = 0; i < 5; i++) {
                sleepPrepareItem = new SleepPrepareItem();
                sleepPrepareItem.setItemType(Constant.ItemType.HABIT_SLEEP_PREPARE);
                sleepPrepareItem.setImageResId(images[i]);
                sleepPrepareItem.setBigImageResId(bigImages[i]);
                sleepPrepareItem.setTitle(getResources().getString(titles[i]));
                sleepPrepareItem.setShortTip(getResources().getString(shortTips[i]));
                sleepPrepareItem.setMessage(getResources().getString(tips[i]));
                mItems.add(sleepPrepareItem);
            }
        }
        mAdapter.setData(mItems);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(BaseRecyclerHolder holder, int position) {
        if (mItems != null && position >= 0 && position < mItems.size() && mItems.get(position) != null) {
            if (mItems.get(position).getItemType() == Constant.ItemType.HABIT_SLEEP_PREPARE) {
                SleepPrepareItem sleepPrepareItem = (SleepPrepareItem) mItems.get(position);
                showTipDialog(sleepPrepareItem.getBigImageResId(), sleepPrepareItem.getTitle(), sleepPrepareItem.getMessage());
            }
        }
    }

    private void showTipDialog(int imageResId, String title, String tip) {
        if (mTipDialog == null) {
            mTipDialog = new PrepareTipDialog();
            WindowManager wm = (WindowManager) this
                    .getSystemService(Context.WINDOW_SERVICE);
            mTipDialog.setSize((int) (wm.getDefaultDisplay().getWidth() * 0.7), 0);
        }
        mTipDialog.setMessage(imageResId, title, tip);
        mTipDialog.show(getSupportFragmentManager());
    }

    @Override
    public void onChange() {
        List<PrepareBean> list = new ArrayList<>();
        for (BaseItem baseItem : mItems) {
            if (baseItem.getItemType() == Constant.ItemType.HABIT_SLEEP_PREPARE) {
                SleepPrepareItem prepareItem=(SleepPrepareItem)baseItem;
                if (prepareItem.getChecked()){
                    PrepareBean prepareBean = new PrepareBean();
                    prepareBean.setSleepPrepareItem((SleepPrepareItem) baseItem);
                    list.add(prepareBean);
                }
            }
        }
        mSleepPrepareView.setData(list);
    }
}
