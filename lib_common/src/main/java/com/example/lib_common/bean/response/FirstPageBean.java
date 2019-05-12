package com.example.lib_common.bean.response;

import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 13:49
 * @email : 15869107730@163.com
 * @note :
 */
public class FirstPageBean {
    private RecommendDataBean recommend_data;
    private List<BannerBean> banner;
    private List<EntranceIconsBean> entranceIcons;
    private List<PartitionsBean> partitions;

    public RecommendDataBean getRecommend_data() {
        return recommend_data;
    }

    public void setRecommend_data(RecommendDataBean recommend_data) {
        this.recommend_data = recommend_data;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<EntranceIconsBean> getEntranceIcons() {
        return entranceIcons;
    }

    public void setEntranceIcons(List<EntranceIconsBean> entranceIcons) {
        this.entranceIcons = entranceIcons;
    }

    public List<PartitionsBean> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<PartitionsBean> partitions) {
        this.partitions = partitions;
    }
}
