package com.example.lib_common.base.bean;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/23
 * @time : 19:23
 * @email : 15869107730@163.com
 * @note :
 */
public class HasMoreItem extends BaseItem {
    private boolean hasMore;

    public HasMoreItem(boolean hasMore, int itemType) {
        this.hasMore = hasMore;
        this.itemType = itemType;
    }

    public boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
