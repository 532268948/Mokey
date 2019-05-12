package com.example.lib_common.bean.response;

import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 13:50
 * @email : 15869107730@163.com
 * @note :
 */
public class RecommendDataBean {
    private PartitionBean partition;
    private List<LivesBean> lives;
    private List<BannerDataBean> banner_data;

    public PartitionBean getPartition() {
        return partition;
    }

    public void setPartition(PartitionBean partition) {
        this.partition = partition;
    }

    public List<LivesBean> getLives() {
        return lives;
    }

    public void setLives(List<LivesBean> lives) {
        this.lives = lives;
    }

    public List<BannerDataBean> getBanner_data() {
        return banner_data;
    }

    public void setBanner_data(List<BannerDataBean> banner_data) {
        this.banner_data = banner_data;
    }
}
