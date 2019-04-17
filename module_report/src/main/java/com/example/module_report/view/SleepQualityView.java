package com.example.module_report.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.module_report.R;
import com.example.lib_common.bean.QualityBean;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 13:48
 * @description:
 */
public class SleepQualityView extends View {

    /**
     * 平均宽度
     */
    private float AVERAGE_WIDTH = 0f;

    /**
     * 睡眠状况数据
     */
    private List<QualityBean> qualityBeanList;

    private Paint mQualityPaint;

    public SleepQualityView(Context context) {
        this(context, null);
    }

    public SleepQualityView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepQualityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mQualityPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mQualityPaint.setColor(getResources().getColor(R.color.sleep_quality_error_color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (qualityBeanList != null && qualityBeanList.size() != 0) {
            AVERAGE_WIDTH = ((float) getWidth()) / ((float) (this.qualityBeanList.size() + 1));
            for (int i = 0; i < qualityBeanList.size(); i++) {
                float x = (i + 1) * AVERAGE_WIDTH;
                RectF oval = new RectF(x - 6f, ((float)getHeight()) * (qualityBeanList.get(i).getGrade()) / 10f, x + 6f, getHeight());
                switch (qualityBeanList.get(i).getType()) {
                    case 0:
                        mQualityPaint.setColor(getResources().getColor(R.color.sleep_quality_error_color));
                        break;
                    case 1:
                        mQualityPaint.setColor(getResources().getColor(R.color.sleep_quality_deep_color));
                        break;
                    case 2:
                        mQualityPaint.setColor(getResources().getColor(R.color.sleep_quality_shallow_color));
                        break;
                    case 3:
                        mQualityPaint.setColor(getResources().getColor(R.color.sleep_quality_dream_color));
                        break;
                    default:
                        break;
                }
                canvas.drawRoundRect(oval, 20, 20, mQualityPaint);
            }
        }
    }

    /**
     * 设置睡眠数据
     *
     * @param qualityBeanList
     */
    public void setData(List<QualityBean> qualityBeanList) {
        this.qualityBeanList = qualityBeanList;
        invalidate();
    }
}
