package com.example.module_report.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.lib_common.bean.QualityBean;
import com.example.module_report.R;

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
                RectF oval = new RectF(x - 6f, ((float) getHeight()) * (qualityBeanList.get(i).getGrade()) / 10f, x + 6f, getHeight());
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

    public Bitmap createBitmap() {

        //由于直接new出来的view是不会走测量、布局、绘制的方法的，所以需要我们手动去调这些方法，不然生成的图片就是黑色的。
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY);

        measure(widthMeasureSpec, heightMeasureSpec);
        layout(0, 0, getWidth(), getHeight());
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        draw(canvas);

        return bitmap;
    }
}
