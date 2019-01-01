package com.example.module_habit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.module_habit.R;

/**
 * author: tianhuaye
 * date:   2018/11/23 13:57
 * description:
 */
public class SleepPrepareView extends View {

    /**
     * 画笔
     */
    private Paint mPaint;

    public SleepPrepareView(Context context) {
        super(context);
    }

    public SleepPrepareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SleepPrepareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDashLine(canvas);
    }

    /**
     * 画虚线
     */
    public void drawDashLine(Canvas canvas){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.sleep_prepare_dash_line_color));
        mPaint.setStrokeWidth(3);
        mPaint.setPathEffect(new DashPathEffect(new float[] {20, 10},0));
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
    }
}
