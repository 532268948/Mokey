package com.example.lib_common.db.dao;

import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DaoUtil;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.db.entity.AlarmDao;
import com.example.lib_common.db.entity.DaoSession;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/28 16:00
 * @description:
 */
public class AlarmDaoUtil extends DaoUtil<Alarm> {

    public AlarmDaoUtil(DBManager dbManager, DaoSession daoSession) {
        super(dbManager, daoSession);
    }

    /**
     * 单个插入
     *
     * @param alarm
     */
    public void insertAlarm(Alarm alarm) {
        insertSingle(alarm);
    }

    public void insertBatchAlarm(List<Alarm> list) {
        insertBatchAlarm(list, null);
    }

    /**
     * 批量插入
     *
     * @param list
     * @param insertListener
     */
    public void insertBatchAlarm(List<Alarm> list, DbOperateListener.OnInsertListener insertListener) {
        setOnInsertListener(insertListener);
        insertBatch(Alarm.class, list);
    }

    /**
     * 批量删除
     *
     * @param list
     */
    public void deleteBatchAlarm(List<Alarm> list) {
        deleteBatchAlarm(list, null);
    }

    /**
     * 批量删除
     *
     * @param list
     * @param deleteListener
     */
    public void deleteBatchAlarm(List<Alarm> list, DbOperateListener.OnDeleteListener deleteListener) {
        setOnDeleteListener(deleteListener);
        deleteBatch(Alarm.class, list);
    }

    /**
     * 单个更新
     */
    public void updateSingleAlarm(Alarm alarm) {
        updateSingleAlarm(alarm, null);
    }

    /**
     * 单个更新
     *
     * @param alarm
     * @param updateListener 更新结果监听回调
     */
    public void updateSingleAlarm(Alarm alarm, DbOperateListener.OnUpdateListener updateListener) {
        setOnUpdateListener(updateListener);
        updateSingle(Alarm.class, alarm);
    }

    /**
     * 批量更新
     *
     * @param list
     */
    public void updateBatchAlarm(List<Alarm> list) {
        updateBatchAlarm(list, null);
    }

    /**
     * 批量更新
     *
     * @param list
     * @param updateListener
     */
    public void updateBatchAlarm(List<Alarm> list, DbOperateListener.OnUpdateListener<List<Alarm>> updateListener) {
        setOnUpdateListener(updateListener);
        updateBatch(Alarm.class, list);
    }

    /**
     * 条件查询
     */
    public void queryWhereAlarm(long memberId) {
        queryWhereAlarm(memberId, null);
    }

    /**
     * 条件查询
     *
     * @param memberId            闹钟id
     * @param querySingleListener 查询结果监听回调
     */
    public void queryWhereAlarm(long memberId, DbOperateListener.OnQuerySingleListener<Alarm> querySingleListener) {
        setOnQuerySingleListener(querySingleListener);
        query(Alarm.class, AlarmDao.Properties.Id.eq(memberId));
    }

    /**
     * 条件查询
     *
     * @param open 闹钟是否开启
     * @param queryAllListener
     */
    public void querwhereAlarm(boolean open,DbOperateListener.OnQueryAllListener<Alarm> queryAllListener){
        setOnQueryAllListener(queryAllListener);
        query(Alarm.class, AlarmDao.Properties.Open.eq(open));
    }

    /**
     * 条件查询
     *
     * @param type 闹钟类型
     */
    public void queryWhereTypeAlarm(int type) {
        queryWhereTypeAlarm(type, null);
    }

    /**
     * 条件查询
     *
     * @param type             闹钟类型
     * @param queryAllListener
     */
    public void queryWhereTypeAlarm(int type, DbOperateListener.OnQueryAllListener<Alarm> queryAllListener) {
        setOnQueryAllListener(queryAllListener);
        queryAll(Alarm.class, daoSession.queryBuilder(Alarm.class).where(AlarmDao.Properties.Type.eq(type)).build());
    }

    /**
     * 查询全部
     */
    public void queryAllAlarm(DbOperateListener.OnQueryAllListener<Alarm> onQueryAllListener) {
        setOnQueryAllListener(onQueryAllListener);
        queryAll(Alarm.class, null);
    }
}
