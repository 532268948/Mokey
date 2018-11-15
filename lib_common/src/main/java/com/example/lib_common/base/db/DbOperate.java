package com.example.lib_common.base.db;

/**
 * author: tianhuaye
 * date:   2018/11/15 15:14
 * description:
 */
public class DbOperate<T> {
    protected DbOperateListener.OnQueryAllListener<T> onQueryAllListener;
    protected DbOperateListener.OnUpdateListener<T> onUpdateListener;
    protected DbOperateListener.OnInsertListener<T> onInsertListener;
    protected DbOperateListener.OnQuerySingleListener<T> onQuerySingleListener;
    protected DbOperateListener.OnDeleteListener<T> onDeleteListener;

    public DbOperateListener.OnQueryAllListener<T> getOnQueryAllListener() {
        return onQueryAllListener;
    }

    public void setOnQueryAllListener(DbOperateListener.OnQueryAllListener<T> onQueryAllListener) {
        this.onQueryAllListener = onQueryAllListener;
    }

    public DbOperateListener.OnUpdateListener<T> getOnUpdateListener() {
        return onUpdateListener;
    }

    public void setOnUpdateListener(DbOperateListener.OnUpdateListener<T> onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    public DbOperateListener.OnInsertListener<T> getOnInsertListener() {
        return onInsertListener;
    }

    public void setOnInsertListener(DbOperateListener.OnInsertListener<T> onIsertListener) {
        this.onInsertListener = onInsertListener;
    }

    public DbOperateListener.OnQuerySingleListener<T> getOnQuerySingleListener() {
        return onQuerySingleListener;
    }

    public void setOnQuerySingleListener(DbOperateListener.OnQuerySingleListener<T> onQuerySingleListener) {
        this.onQuerySingleListener = onQuerySingleListener;
    }

    public DbOperateListener.OnDeleteListener<T> getOnDeleteListener() {
        return onDeleteListener;
    }

    public void setOnDeleteListener(DbOperateListener.OnDeleteListener<T> onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

}
