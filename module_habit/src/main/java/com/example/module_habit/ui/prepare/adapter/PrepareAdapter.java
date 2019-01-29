package com.example.module_habit.ui.prepare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib_common.base.adapter.BaseRecyclerAdapter;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.bean.BaseItem;
import com.example.lib_common.base.bean.SleepPrepareItem;
import com.example.lib_common.common.Constant;
import com.example.module_habit.R;

/**
 * @author: tianhuaye
 * @date: 2018/11/23 14:46
 * @description:
 */
public class PrepareAdapter extends BaseRecyclerAdapter {

    private boolean isMax = false;
    private final int MAX_SIZE = 3;
    private OnSelectChangeListener onSelectChangeListener;

    public PrepareAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.habit_prepare_tip_item, viewGroup, false);
        return new PrepareViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        if (mItems != null && position >= 0 && position < mItems.size() && mItems.get(position) != null) {
            BaseItem baseItem = mItems.get(position);
            if (baseItem.getItemType() == Constant.ItemType.HABIT_SLEEP_PREPARE) {
                final PrepareViewHolder prepareViewHolder = (PrepareViewHolder) holder;
                final SleepPrepareItem sleepPrepareItem = (SleepPrepareItem) baseItem;
                //选中数量到达最大
                if (isMax) {
                    if (sleepPrepareItem.getChecked()) {
                        prepareViewHolder.mSelectCb.setEnabled(true);
                    } else {
                        prepareViewHolder.mSelectCb.setEnabled(false);
                    }
                } else {
                    prepareViewHolder.mSelectCb.setEnabled(true);
                }
                prepareViewHolder.mIconIv.setImageResource(sleepPrepareItem.getImageResId());
                prepareViewHolder.mTitleTv.setText(sleepPrepareItem.getTitle());
                prepareViewHolder.mTipTv.setText(sleepPrepareItem.getShortTip());
                if (sleepPrepareItem.getChecked()) {
                    prepareViewHolder.mSelectCb.setChecked(true);
                    prepareViewHolder.mSelectCb.setText(String.valueOf(sleepPrepareItem.getCheckNum()));
                } else {
                    prepareViewHolder.mSelectCb.setChecked(false);
                    prepareViewHolder.mSelectCb.setText(mContext.getResources().getString(R.string.empty_text));
                }
                prepareViewHolder.mSelectCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        updateState(sleepPrepareItem, isChecked);
                        if (onSelectChangeListener!=null){
                            onSelectChangeListener.onChange();
                        }
                    }
                });

            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    /**
     * 更新状态
     *
     * @param sleepPrepareItem
     * @param isCheck
     */
    public void updateState(SleepPrepareItem sleepPrepareItem, boolean isCheck) {
        if (isCheck) {
            //未到最大数量
            if (!isMax) {
                int i = 0;
                for (BaseItem baseItem : mItems) {
                    if (baseItem.getItemType() == Constant.ItemType.HABIT_SLEEP_PREPARE) {
                        SleepPrepareItem prepareItem = (SleepPrepareItem) baseItem;
                        if (prepareItem.getChecked()) {
                            i++;
                        }
                    }
                }
                i++;
                if (i > 0 && i <= MAX_SIZE) {
                    sleepPrepareItem.setChecked(true);
                    sleepPrepareItem.setCheckNum(i);
                }

                if (i == MAX_SIZE) {
                    isMax = true;
                }
                notifyDataSetChanged();
            }
        } else {//取消选中
            if (sleepPrepareItem.getChecked()) {
                for (BaseItem baseItem : mItems) {
                    if (baseItem.getItemType() == Constant.ItemType.HABIT_SLEEP_PREPARE) {
                        SleepPrepareItem prepareItem = (SleepPrepareItem) baseItem;
                        if (prepareItem.getChecked()) {
                            if (prepareItem.getCheckNum() > sleepPrepareItem.getCheckNum()) {
                                prepareItem.setCheckNum(prepareItem.getCheckNum() - 1);
                            }
                        }
                    }
                }
                //清除选中信息
                sleepPrepareItem.setChecked(false);
                sleepPrepareItem.setCheckNum(0);
                isMax = false;
                notifyDataSetChanged();
            }
        }
    }

    public void setSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        this.onSelectChangeListener = onSelectChangeListener;
    }

    public interface OnSelectChangeListener {
        void onChange();
    }


    private static class PrepareViewHolder extends BaseRecyclerHolder {

        private ImageView mIconIv;
        private TextView mTitleTv;
        private TextView mTipTv;
        private CheckBox mSelectCb;

        public PrepareViewHolder(@NonNull View itemView, Context context) {
            super(itemView, context);
            mIconIv = itemView.findViewById(R.id.iv_icon);
            mTitleTv = itemView.findViewById(R.id.tv_title);
            mTipTv = itemView.findViewById(R.id.tv_tip);
            mSelectCb = itemView.findViewById(R.id.cb_select);
        }
    }
}

