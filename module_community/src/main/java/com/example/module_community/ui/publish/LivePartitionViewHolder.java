package com.example.module_community.ui.publish;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lib_common.bean.response.PartitionBean;
import com.example.module_community.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 17:10
 * @email : 15869107730@163.com
 * @note :
 */
public class LivePartitionViewHolder extends RecyclerView.ViewHolder {
    private Context context;

    private ImageView itemIcon;
    private TextView itemTitle;
    private TextView itemCount;
    public LivePartitionViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context=context;
        itemIcon=itemView.findViewById(R.id.item_live_partition_icon);
        itemTitle=itemView.findViewById(R.id.item_live_partition_title);
        itemCount=itemView.findViewById(R.id.item_live_partition_count);
    }

    public void setInfo(PartitionBean partition){
        Glide.with(context)
                .load(partition.getSub_icon().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemIcon);
        itemTitle.setText(partition.getName());
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(
                "当前" + partition.getCount() + "个直播");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.pink_text_color));
        stringBuilder.setSpan(foregroundColorSpan, 2,
                stringBuilder.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemCount.setText(stringBuilder);
    }
}
