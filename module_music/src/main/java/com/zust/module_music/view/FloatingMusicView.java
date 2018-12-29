package com.zust.module_music.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.lib_common.R;
import com.example.lib_common.base.bean.MusicItem;
import com.example.lib_common.util.ViewUtil;
import com.zust.module_music.ui.play.MusicPlayActivity;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/16
 * @time : 22:10
 * @email : 15869107730@163.com
 * @note : 音乐播放悬浮窗
 */
public class FloatingMusicView extends RelativeLayout {

    private ImageView mCoverIv;
    private ImageView mSmallPlayIv;
    private ImageView narrowImg;
    private ImageView preImg;
    private ImageView nextImg;
    private ImageView playImg;
    private ImageView tagImg;
    private TextSwitcher titleTv;
    private TextView tagTv;
    private Space space;
    private LinearLayout mContainerLl;

    private int mDrawableLevel = 0;

    /**
     * 大浮窗背景色
     */
    private int mBigBgColor = 0;
    /**
     * 小浮窗背景色
     */
    private int mSmallBgColor = 0;
    /**
     * 大浮窗圆角
     */
    private int mBigRadius;
    /**
     * false big true small
     */
    private boolean mType;

    /**
     * 是否正在播放
     */
    private boolean isPlaying = true;

    private GradientDrawable mDrawable;

    private MusicItem mMusicItem;

    private OnMusicControlClickListener listener = new OnMusicControlClickListener() {
        @Override
        public void onPlayClick(MusicItem musicItem) {

        }

        @Override
        public void onNextClick(MusicItem musicItem) {

        }

        @Override
        public void onPreClick(MusicItem musicItem) {

        }

        @Override
        public void onNarrowClick(boolean type) {

        }
    };

    public FloatingMusicView(Context context) {
        this(context, null);
    }

    public FloatingMusicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.music_play, this, true);
        mCoverIv = findViewById(R.id.iv_music_cover);
        mSmallPlayIv = findViewById(R.id.iv_small_play_tag);
        narrowImg = findViewById(R.id.iv_music_narrow);
        preImg = findViewById(R.id.iv_music_pre);
        nextImg = findViewById(R.id.iv_music_next);
        playImg = findViewById(R.id.iv_music_play);
        titleTv = findViewById(R.id.tv_music_album);
        tagTv = findViewById(R.id.tv_music_title);
        tagImg = findViewById(R.id.iv_music_play_tag);
        space = findViewById(R.id.vertical_space);
        mContainerLl = findViewById(R.id.ll_container);

        mDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.music_shape_floating_view_bg).mutate();
        mDrawable.setCornerRadius(mBigRadius);
        setBackgroundDrawable(mDrawable);
        titleTv.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView.setTextColor(getContext().getResources().getColor(R.color.music_floating_view_title_color));
                return textView;
            }
        });

        mSmallPlayIv.setOnClickListener(clickListener);
        narrowImg.setOnClickListener(clickListener);
        preImg.setOnClickListener(clickListener);
        nextImg.setOnClickListener(clickListener);
        playImg.setOnClickListener(clickListener);
        mCoverIv.setOnClickListener(clickListener);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.FloatingMusicView, 0, 0);
        if (attr == null) {
            return;
        }
        try {
            mBigBgColor = attr.getColor(R.styleable.FloatingMusicView_big_bg_color, getResources().getColor(R.color.music_floating_bg));
            mSmallBgColor = attr.getColor(R.styleable.FloatingMusicView_small_bg_color, getResources().getColor(R.color.music_floating_bg));
            mBigRadius = attr.getDimensionPixelOffset(R.styleable.FloatingMusicView_big_radius, 12);
        } finally {
            attr.recycle();
        }
    }

    public void addMusicControlClickListener(OnMusicControlClickListener listener) {
        this.listener = listener;
    }

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

            } else if (i == R.id.iv_music_narrow) {
                if (listener != null) {
                    if (!mType) {
                        startAnimation(mType);
                    }
                }
            } else if (i == R.id.iv_small_play_tag) {
                if (listener != null) {
                    if (mType) {
                        startAnimation(mType);
                    }
                }
            } else if (i == R.id.iv_music_cover) {
                getContext().startActivity(new Intent(getContext(), MusicPlayActivity.class));
            }
        }
    }, 500);

//    public void setFMAudio(PgntFmItem item) {
//        if (item != null && item.mLibFM != null) {
//            mFMItem = item;
//            item.isPlaying = false;
//            BBMusicItem bbMusicItem = BBMusicHelper.getBBCurMusicItem();
//            String title = null;
//            if (bbMusicItem != null) {
//                title = bbMusicItem.musicName;
//                boolean isPlayFm = bbMusicItem.bbSource == BBSource.FM;
//                if (isPlayFm) {
//                    if (item.mLibFM.getId() != null && bbMusicItem.setId == item.mLibFM.getId() && BBMusicHelper
//                            .getBBState() ==
//                            BBState.Playing) {
//                        item.isPlaying = true;
//                    }
//                }
//            }
//            if (!item.isPlaying) {
//                List<LibAudio> audioList = item.mLibFM.getAudioList();
//                if (audioList != null && !audioList.isEmpty()) {
//                    if (audioList.get(0) != null) {
//                        title = audioList.get(0).getTitle();
//                    }
//                }
//            }
//            if (!TextUtils.isEmpty(item.name)) {
//                albumTv.setText(item.name);
//            } else {
//                albumTv.setText(R.string.antenatal_training_daily);
//            }
//            setAudio(title);
//            if (item.isPlaying) {
//                if (!isAnimation) {
//                    tagImg.postDelayed(runnable, 250);
//                    isAnimation = true;
//                }
//                playImg.setImageResource(R.drawable.icon_music_pause);
//            } else {
//                tagImg.removeCallbacks(runnable);
//                isAnimation = false;
//                playImg.setImageResource(R.drawable.icon_music_play);
//            }
//        }
//    }

    /**
     * 设置标题
     *
     * @param title 音乐名字
     */
    public void setAudio(String title) {
        if (!TextUtils.isEmpty(title)) {
            ViewUtil.setViewVisible(titleTv);
            titleTv.setText(getResources().getString(R.string.music_floating_title, title));
        } else {
            ViewUtil.setViewGone(titleTv);
        }
    }

    /**
     * 浮窗大小变化动画
     *
     * @param type false变小 true变大
     */
    private void startAnimation(boolean type) {
        if (type) {
            morphToBig();
        } else {
            morphToSmall();
        }
    }

    /**
     * 缩小动画
     */
    private void morphToSmall() {
        ViewUtil.setViewGone(mCoverIv);
        ViewUtil.setViewGone(narrowImg);
        ViewUtil.setViewGone(nextImg);
        ViewUtil.setViewGone(playImg);
        ViewUtil.setViewGone(preImg);
        ViewUtil.setViewGone(space);
        ViewUtil.setViewGone(titleTv);
        ViewUtil.setViewGone(mContainerLl);
        MorphingAnimation animation = createProgressMorphing(mBigRadius, getHeight(), getWidth(), getHeight());

        animation.setFromColor(mBigBgColor);
        animation.setToColor(mSmallBgColor);
        animation.setListener(new MorphingAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                mType = true;
                ViewUtil.setViewVisible(mSmallPlayIv);
                smallPlayAnimation(isPlaying);
            }
        });
        animation.start();
    }

    /**
     * 变大动画
     */
    private void morphToBig() {
        ViewUtil.setViewGone(mSmallPlayIv);
        MorphingAnimation animation = createProgressMorphing(getHeight(), mBigRadius, getHeight(), getWidth());

        animation.setFromColor(mSmallBgColor);
        animation.setToColor(mBigBgColor);
        animation.setListener(new MorphingAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                mType = false;
                ViewUtil.setViewVisible(narrowImg);
                ViewUtil.setViewVisible(nextImg);
                ViewUtil.setViewVisible(playImg);
                ViewUtil.setViewVisible(preImg);
                ViewUtil.setViewVisible(space);
                ViewUtil.setViewVisible(titleTv);
                ViewUtil.setViewVisible(mContainerLl);
                ViewUtil.setViewVisible(mCoverIv);
                bigPlayAnimation(isPlaying);
            }
        });
        animation.start();
    }

    /**
     * 大浮窗播放动画是否播放
     *
     * @param isPlay false不播放 true播放
     */
    private void bigPlayAnimation(boolean isPlay) {
        if (tagImg != null) {
            AnimationDrawable animationDrawable = (AnimationDrawable) tagImg.getDrawable();
            if (animationDrawable != null) {
                if (isPlay) {
                    animationDrawable.start();
                } else {
                    animationDrawable.stop();
                }
            }
        }
    }

    /**
     * 小浮窗播放动画是否播放
     *
     * @param isPlay false不播放 true播放
     */
    private void smallPlayAnimation(boolean isPlay) {
        if (mSmallPlayIv != null) {
            AnimationDrawable animationDrawable = (AnimationDrawable) mSmallPlayIv.getDrawable();
            if (animationDrawable != null) {
                if (isPlay) {
                    animationDrawable.start();
                } else {
                    animationDrawable.stop();
                }
            }
        }
    }

    private MorphingAnimation createProgressMorphing(float fromCorner, float toCorner, int fromWidth, int toWidth) {

        MorphingAnimation animation = new MorphingAnimation(this, mDrawable);
        animation.setFromCornerRadius(fromCorner);
        animation.setToCornerRadius(toCorner);
        animation.setPadding(0);
        animation.setFromWidth(fromWidth);
        animation.setToWidth(toWidth);
        animation.setDuration(MorphingAnimation.DURATION_NORMAL);
        return animation;
    }


    /**
     * 音乐播放监听器
     */
    public interface OnMusicControlClickListener {
        /**
         * 开始播放
         *
         * @param musicItem
         */
        void onPlayClick(MusicItem musicItem);

        /**
         * 下一首
         *
         * @param musicItem
         */
        void onNextClick(MusicItem musicItem);

        /**
         * 前一首
         *
         * @param musicItem
         */
        void onPreClick(MusicItem musicItem);

        /**
         * 改变浮窗大小按钮
         *
         * @param type false当前为Big true当前为Small
         */
        void onNarrowClick(boolean type);
    }
}
