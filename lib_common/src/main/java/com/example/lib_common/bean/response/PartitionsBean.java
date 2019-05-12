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
public class PartitionsBean {
    private PartitionBean partition;
    private List<LivesBean> lives;

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
}
