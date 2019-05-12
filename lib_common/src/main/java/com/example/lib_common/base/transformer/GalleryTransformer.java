package com.example.lib_common.base.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * author: tianhuaye
 * date:   2018/11/21 16:26
 * description:
 */
public class GalleryTransformer implements ViewPager.PageTransformer {
    public static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {

//        Log.e("GalleryTransformer", "transformPage: "+page.getTag()+" "+position);

        if (position < -1) { // [-Infinity,-1)
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);

        } else if (position <= 1) { // [-1,1]
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);

        }

    }
}
