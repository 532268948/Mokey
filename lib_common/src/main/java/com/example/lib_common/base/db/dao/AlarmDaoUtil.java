package com.example.lib_common.base.db.dao;

import com.example.lib_common.base.db.DBManager;
import com.example.lib_common.base.db.DaoUtil;
import com.example.lib_common.base.db.DbOperateListener;
import com.example.lib_common.base.db.entity.Alarm;
import com.example.lib_common.base.db.entity.AlarmDao;
import com.example.lib_common.base.db.entity.DaoSession;

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

    /**
     * 单个更新
     */
    public void updateSingleAlarm(Alarm alarm) {
        updateSingleAlarm(alarm,null);
    }

    /**
     * 单个更新
     * @param alarm
     * @param updateListener 更新结果监听回调
     */
    public void updateSingleAlarm(Alarm alarm, DbOperateListener.OnUpdateListener updateListener) {
        setOnUpdateListener(updateListener);
        updateSingle(Alarm.class, alarm);
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
    public void queryWhereAlarm(long memberId, DbOperateListener.OnQuerySingleListener querySingleListener) {
        setOnQuerySingleListener(querySingleListener);
        query(Alarm.class, AlarmDao.Properties.Id.eq(memberId));
    }

    /**
     * 查询全部
     */
    public void queryAllAlarm(DbOperateListener.OnQueryAllListener onQueryAllListener) {
        setOnQueryAllListener(onQueryAllListener);
        queryAll(Alarm.class, null);
    }
}
