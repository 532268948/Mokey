package com.example.lib_common.base.db.dao;

import com.example.lib_common.base.db.DBManager;
import com.example.lib_common.base.db.DaoUtil;
import com.example.lib_common.base.db.DbOperateListener;
import com.example.lib_common.base.db.entity.DaoSession;
import com.example.lib_common.base.db.entity.MusicBefore;
import com.example.lib_common.common.Constant;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/23
 * @time : 16:23
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicBeforeDao extends DaoUtil<MusicBefore> {
    public MusicBeforeDao(DBManager dbManager, DaoSession daoSession) {
        super(dbManager, daoSession);
    }

    /**
     * 分页批量查询睡前小曲数据
     *
     * @param currentPage
     * @param whereCondition
     */
    public void queryWhereMusic(int currentPage, WhereCondition whereCondition, DbOperateListener.OnQueryAllListener onQueryAllListener) {
        setOnQueryAllListener(onQueryAllListener);
        query(MusicBefore.class, currentPage, Constant.ListPageSize.MUSIC_SLEEP_BEFORE_PAGE_SIZE, whereCondition);
    }

    /**
     * 批量插入
     */
    public void insertBatchMusic(List<MusicBefore> list) {
        insertBatch(MusicBefore.class, list);
    }
}
