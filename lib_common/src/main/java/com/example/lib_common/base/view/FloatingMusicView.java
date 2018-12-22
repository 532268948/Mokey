package com.example.lib_common.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib_common.R;
import com.example.lib_common.base.bean.MusicItem;
import com.example.lib_common.util.ScreenUtil;
import com.example.lib_common.util.ViewUtil;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/16
 * @time : 22:10
 * @email : 15869107730@163.com
 * @note : 音乐播放悬浮窗
 */
public class FloatingMusicView extends FrameLayout {

    private ImageView preImg;
    private ImageView nextImg;
    private ImageView playImg;
    private ImageView tagImg;
    private TextView albumTv;
    private TextView titleTv;

    private int mDrawableLevel = 0;

    private MusicItem mMusicItem;

    private OnMusicControlClickListener listener;

    public FloatingMusicView(Context context) {
        this(context, null);
    }

    public FloatingMusicView( Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingMusicView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.music_play, this, true);
        int padding = ScreenUtil.dp2px(context, 12);
        setPadding(padding, 0, padding, 0);
        setBackgroundColor(getResources().getColor(R.color.music_floating_bg));
        preImg = (ImageView) findViewById(R.id.iv_music_pre);
        nextImg = (ImageView) findViewById(R.id.iv_music_next);
        playImg = (ImageView) findViewById(R.id.iv_music_play);
        albumTv = (TextView) findViewById(R.id.tv_music_album);
        titleTv = (TextView) findViewById(R.id.tv_music_title);
        tagImg = (ImageView) findViewById(R.id.iv_music_play_tag);

        preImg.setOnClickListener(clickListener);
        nextImg.setOnClickListener(clickListener);
        playImg.setOnClickListener(clickListener);
    }

    public void addMusicControlClickListener(OnMusicControlClickListener listener) {
        this.listener = listener;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mDrawableLevel++;
            if (mDrawableLevel > 8) {
                mDrawableLevel = 0;
            }
            tagImg.setImageLevel(mDrawableLevel);
            tagImg.postDelayed(runnable, 250);
        }
    };

    private View.OnClickListener clickListener = ViewUtil.createInternalClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.iv_music_pre) {
                if (listener != null) {
                    listener.onPreClick(mMusicItem);
                }

            } else if (i == R.id.iv_music_play) {
                if (listener != null) {
                    listener.onPlayClick(mMusicItem);
                }

            } else if (i == R.id.iv_music_next) {
                if (listener != null) {
                    listener.onNextClick(mMusicItem);
                }

            }
        }
    }, 500);

    /**
     * 音乐播放监听器
     */
    public interface OnMusicControlClickListener {
        void onPlayClick(MusicItem musicItem);

        void onNextClick(MusicItem musicItem);

        void onPreClick(MusicItem musicItem);
    }
}
