package com.example.module_community.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lib_common.bean.response.LivesBean;
import com.example.module_community.R;
import com.example.module_community.ui.play.PlayerActivity;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 15:02
 * @email : 15869107730@163.com
 * @note :
 */
public class LiveViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ImageView itemLiveCover;
    private TextView itemLiveUser;
    private TextView itemLiveTitle;
    private ImageView itemLiveUserCover;
    private TextView itemLiveCount;
    private CardView itemLiveLayout;

    public LiveViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        itemLiveCover = itemView.findViewById(R.id.item_live_cover);
        itemLiveUser = itemView.findViewById(R.id.item_live_user);
        itemLiveTitle = itemView.findViewById(R.id.item_live_title);
        itemLiveUserCover = itemView.findViewById(R.id.item_live_user_cover);
        itemLiveCount = itemView.findViewById(R.id.item_live_count);
        itemLiveLayout = itemView.findViewById(R.id.item_live_layout);
    }

    public void setInfo(LivesBean livesBean) {
        if (livesBean==null){
            return;
        }
        Glide.with(context)
                .load(livesBean.getOwner().getFace())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(itemLiveCover);

        Glide.with(context)
                .load(livesBean.getCover().getSrc())
                .centerCrop()
                .dontAnimate()
//                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemLiveUserCover);

        itemLiveTitle.setText(livesBean.getTitle());
        itemLiveUser.setText(livesBean.getOwner().getName());
        itemLiveCount.setText(String.valueOf(livesBean.getOnline()));
     itemLiveLayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(context,PlayerActivity.class);
             intent.putExtra("url",livesBean.getPlayurl());
             context.startActivity(intent);
         }
     });
    }
}
