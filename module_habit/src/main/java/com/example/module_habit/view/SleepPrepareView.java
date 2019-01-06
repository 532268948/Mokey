package com.example.module_habit.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.lib_common.util.ScreenUtil;
import com.example.module_habit.R;
import com.example.module_habit.bean.PrepareBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2018/11/23 13:57
 * @description:
 */
public class SleepPrepareView extends View {

    private final String TAG = "SleepPrepareView";
    private final int MOVE_DISTANCE = 1;
    private final float PREPARE_TOTAL_TIME = 30f;
    private final float PREPARE_MIN_TIME = 5f;
    private int DASH_LINE_HEIGHT;

    private int tag = -1;

    private float downX = 0;
    private float downY = 0;

    /**
     * 虚线画笔
     */
    private Paint mDashLinePaint;

    /**
     * 直线画笔
     */
    private Paint mLinePaint;

    /**
     * 圆形画笔
     */
    private Paint mCirclePaint;

    /**
     * tag画笔
     */
    private Paint mBitmapPaint;

    /**
     * 时间文字画笔
     */
    private Paint mTextPaint;

    /**
     * 图标画笔
     */
    private Paint mIconPaint;

    /**
     * 虚线指示图
     */
    private Bitmap mTagBitmap;


    private List<PrepareBean> mPrepareList;


    public SleepPrepareView(Context context) {
        this(context, null);
    }

    public SleepPrepareView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepPrepareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPrepareList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PrepareBean prepareBean = new PrepareBean();
            prepareBean.setTime((i + 1) * 10);
            prepareBean.setIcon(R.drawable.habit_prepare_tip_1);
            mPrepareList.add(prepareBean);
        }
        init();
    }

    private void init() {
        DASH_LINE_HEIGHT = ScreenUtil.dp2px(this.getContext(), 100);
        initPaint();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: ");
        mTagBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.habit_dash_line_tag);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure: ");
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mTagBitmap.getHeight() + ScreenUtil.dp2px(this.getContext(), 100), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas, 0, getHeight() / 3, getWidth(), getHeight() / 3);
        if (mPrepareList != null) {
            for (int i = 0; i < mPrepareList.size() - 1; i++) {
                drawDashLine(canvas, mPrepareList.get(i));
                drawTagBitmap(canvas, mPrepareList.get(i));
            }
            for (int i = 0; i < mPrepareList.size(); i++) {
                if (i == 0) {
                    drawTimeText(canvas, 0f, mPrepareList.get(i).getTime());
                    drawIcon(canvas, 0f, mPrepareList.get(i).getTime(), mPrepareList.get(i).getIcon());
                } else if (i > 0) {
                    drawTimeText(canvas, mPrepareList.get(i - 1).getTime(), mPrepareList.get(i).getTime());
                    drawIcon(canvas, mPrepareList.get(i - 1).getTime(), mPrepareList.get(i).getTime(), mPrepareList.get(i).getIcon());
                }
            }
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mDashLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDashLinePaint.setColor(getResources().getColor(R.color.sleep_prepare_dash_line_color));
        mDashLinePaint.setStrokeWidth(3);
        mDashLinePaint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 0));
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(getResources().getColor(R.color.sleep_prepare_line_color));
        mLinePaint.setStrokeWidth(8);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(getResources().getColor(R.color.sleep_prepare_circle_color));
        mCirclePaint.setStyle(Paint.Style.FILL);
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(getResources().getColor(R.color.sleep_prepare_time_color));
        mTextPaint.setTextSize(50);
        mIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 画直线
     *
     * @param canvas
     * @param startX
     * @param startY
     * @param stopX
     * @param stopY
     */
    private void drawLine(Canvas canvas, int startX, int startY, int stopX, int stopY) {
        canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
    }

    /**
     * 画虚线和交点圆
     *
     * @param canvas
     */
    private void drawDashLine(Canvas canvas, PrepareBean prepareBean) {
        setLayerType(LAYER_TYPE_SOFTWARE, mDashLinePaint);
        canvas.drawLine(prepareBean.getTime() / PREPARE_TOTAL_TIME * getWidth(), 0, prepareBean.getTime() / PREPARE_TOTAL_TIME * getWidth(), getHeight() * 2 / 3, mDashLinePaint);
        canvas.drawCircle(prepareBean.getTime() / PREPARE_TOTAL_TIME * getWidth(), getHeight() / 3, 10, mCirclePaint);
    }

    /**
     * 画虚线指示器
     *
     * @param canvas
     * @param prepareBean
     */
    private void drawTagBitmap(Canvas canvas, PrepareBean prepareBean) {
        canvas.drawBitmap(mTagBitmap, prepareBean.getTime() / PREPARE_TOTAL_TIME * getWidth() - mTagBitmap.getWidth() / 2, DASH_LINE_HEIGHT, mBitmapPaint);
    }

    /**
     * 画时间文字
     *
     * @param canvas
     * @param startTime
     * @param endTime
     */
    private void drawTimeText(Canvas canvas, float startTime, float endTime) {
        String text = String.format(getResources().getString(R.string.sleep_prepare_time), endTime - startTime);
        float textWidth = mTextPaint.measureText(text);
        canvas.drawText(text, startTime / PREPARE_TOTAL_TIME * getWidth() + ((endTime - startTime) / PREPARE_TOTAL_TIME * getWidth() - textWidth) / 2f, (float) DASH_LINE_HEIGHT, mTextPaint);
    }

    /**
     * 提示图片
     *
     * @param canvas
     * @param startTime
     * @param endTime
     * @param resourceId
     */
    private void drawIcon(Canvas canvas, float startTime, float endTime, int resourceId) {
        if (resourceId != 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
            canvas.drawBitmap(bitmap, startTime / PREPARE_TOTAL_TIME * getWidth() + ((endTime - startTime) / PREPARE_TOTAL_TIME * getWidth() - bitmap.getWidth()) / 2f, ((float) DASH_LINE_HEIGHT / 2f - bitmap.getHeight()) / 2f, mIconPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mPrepareList != null && mTagBitmap != null) {
                    downX = event.getX();
                    downY = event.getY();
                    for (int i = 0; i < mPrepareList.size(); i++) {
                        if (downX >= mPrepareList.get(i).getTime() / PREPARE_TOTAL_TIME * getWidth() - mTagBitmap.getWidth() / 2 && downX <= mPrepareList.get(i).getTime() / PREPARE_TOTAL_TIME * getWidth() + mTagBitmap.getWidth() / 2 && downY >= DASH_LINE_HEIGHT && downY <= DASH_LINE_HEIGHT + mTagBitmap.getHeight()) {
//                            Log.e(TAG, "onTouchEvent: DOWN downX:" + downX + "downY:" + downY + "left:" + (mPrepareList.get(i).getTime() / PREPARE_TOTAL_TIME * getWidth() - mTagBitmap.getWidth() / 2) +
//                                    "top:" + DASH_LINE_HEIGHT + "right:" + (mPrepareList.get(i).getTime() / PREPARE_TOTAL_TIME * getWidth() + mTagBitmap.getWidth() / 2) + "bottom:" + (DASH_LINE_HEIGHT + mTagBitmap.getHeight()));
                            tag = i;
                            break;
                        }
                    }
                } else {
                    tag = -1;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPrepareList != null && tag != -1 && mTagBitmap != null) {
                    float moveX = event.getX() - downX;
                    downX = event.getX();
//                    Log.e(TAG, "onTouchEvent: MOVE" + moveX + "X:" + event.getX());
                    if (Math.abs(moveX) > MOVE_DISTANCE) {
                        mPrepareList.get(tag).setTime(mPrepareList.get(tag).getTime() + moveX * PREPARE_TOTAL_TIME / (float) getWidth());
                        //向左滑
                        if (moveX < 0) {
                            //是第一条虚线
                            if (tag == 0) {
                                //小于最短时间
                                if (mPrepareList.get(tag).getTime() < PREPARE_MIN_TIME) {
                                    mPrepareList.get(tag).setTime(5f);
                                }
                            } else {//非第一条虚线
                                if (mPrepareList.get(tag).getTime() - mPrepareList.get(tag - 1).getTime() < PREPARE_MIN_TIME) {
                                    mPrepareList.get(tag).setTime(mPrepareList.get(tag - 1).getTime() + PREPARE_MIN_TIME);
                                }
                            }

                        } else {//向左滑
                            //是最右边的虚线
                            if (tag == mPrepareList.size() - 1) {
                                //小于最短时间
                                if (PREPARE_TOTAL_TIME - mPrepareList.get(tag).getTime() < PREPARE_MIN_TIME) {
                                    mPrepareList.get(tag).setTime(PREPARE_TOTAL_TIME - PREPARE_MIN_TIME);
                                }
                            } else {//非最右边的虚线
                                if (mPrepareList.get(tag + 1).getTime() - mPrepareList.get(tag).getTime() < PREPARE_MIN_TIME) {
                                    mPrepareList.get(tag).setTime(mPrepareList.get(tag + 1).getTime() - PREPARE_MIN_TIME);
                                }
                            }
                        }
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "onTouchEvent: UP");
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 设置初始化数据
     *
     * @param mPrepareList
     */
    public void setPrepareList(List<PrepareBean> mPrepareList) {
        this.mPrepareList = mPrepareList;
    }
}
