package com.zust.module_music.ui.select;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib_common.bean.RingtoneBean;
import com.zust.module_music.R;

import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/24
 * @time : 10:51
 * @email : 15869107730@163.com
 * @note :
 */
public class SystemMusicAdapter extends RecyclerView.Adapter<SystemMusicAdapter.ViewHolder> {

    private Context mContext;
    private List<RingtoneBean> musicList;
    private OnItemClickListener onItemClickListener;
    private int lastPlay = -1;
//    private MediaPlayer mMediaPlayer;

    public SystemMusicAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_system_music, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (musicList.get(i) != null) {
            viewHolder.mMusicNumberTv.setText((i + 1) + "");
            viewHolder.mNameTv.setText(musicList.get(i).getRingtone().getTitle(mContext));
            viewHolder.mStatusIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e("SystemMusicAdapter", "onClick: ");
                    if (lastPlay >= 0 && lastPlay < musicList.size()) {
                        musicList.get(lastPlay).getRingtone().stop();
                    }
                    musicList.get(i).getRingtone().play();
                    lastPlay = i;
//                    mMediaPlayer = MediaPlayer.create(context, musicList.get(i).getU);
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(viewHolder.getLayoutPosition());
                    }
                }
            });
        }
    }

    public void stopPlay(){
        if (musicList!=null){
            if (lastPlay>=0&&lastPlay<musicList.size()){
                if (musicList.get(lastPlay).getRingtone().isPlaying()){
                    musicList.get(lastPlay).getRingtone().stop();
                }
            }
        }

    }

    public List<RingtoneBean> getData() {
        return musicList;
    }

    @Override
    public int getItemCount() {
        return musicList == null ? 0 : musicList.size();
    }

    public void setData(List<RingtoneBean> musicList) {
        this.musicList = musicList;
        notifyDataSetChanged();
    }

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mMusicNumberTv;
        private ImageView mListeneingIv;
        private TextView mNameTv;
        private ImageView mStatusIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMusicNumberTv = itemView.findViewById(R.id.tv_music_number);
            mListeneingIv = itemView.findViewById(R.id.iv_listening);
            mNameTv = itemView.findViewById(R.id.tv_music_name);
            mStatusIv = itemView.findViewById(R.id.iv_status);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
