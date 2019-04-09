package com.example.module_report.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.bean.BaseItem;
import com.example.lib_common.common.Constant;
import com.example.module_report.bean.ReportBean;
import com.example.module_report.view.ReportView;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 10:53
 * @description:
 */
public class ReportPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<BaseItem> reportBeanList;

    public ReportPagerAdapter(Context context, List<BaseItem> reportBeanList) {
        this.mContext = context;
        this.reportBeanList = reportBeanList;
    }

    @Override
    public int getCount() {
        if (reportBeanList == null) {
            return 0;
        }
        if (reportBeanList.size() == 1) {
            return 1;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (reportBeanList != null && reportBeanList.size() != 0) {
            ReportView view = new ReportView(mContext);

            if (reportBeanList!=null&&reportBeanList.get(position%reportBeanList.size())!=null){
                if (reportBeanList.get(position%reportBeanList.size()).getItemType()==Constant.ItemType.SLEEP_REPORT){
                    ReportBean reportBean=(ReportBean)reportBeanList.get(position%reportBeanList.size());
                    view.setData(reportBean);
                }
            }

            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ReportView view = (ReportView) object;
        container.removeView(view);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
