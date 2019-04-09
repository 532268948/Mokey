package com.example.lib_common.db.dao;

import com.example.lib_common.common.Constant;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DaoUtil;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.DaoSession;
import com.example.lib_common.db.entity.MusicBefore;

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
     * 批量插入
     */
    public void insertBatchMusic(List<MusicBefore> list) {
        insertBatch(MusicBefore.class, list);
    }

    /**
     * 单个歌曲插入
     *
     * @param musicBefore
     */
    public void updateSingleMusic(MusicBefore musicBefore) {
        updateSingle(MusicBefore.class, musicBefore);
    }


    public void queryMusicById(long id) {
        this.onQuerySingleListener = null;
        queryMusicById(id, onQuerySingleListener);
    }

    public void queryMusicById(long id, DbOperateListener.OnQuerySingleListener onQuerySingleListener) {
        setOnQuerySingleListener(onQuerySingleListener);
        query(MusicBefore.class, com.example.lib_common.db.entity.MusicBeforeDao.Properties.Id.eq(id));
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
     * 查询所有睡前小曲歌曲
     * @param onQueryAllListener
     */
    public void queryAllMusic(DbOperateListener.OnQueryAllListener<MusicBefore> onQueryAllListener){
        setOnQueryAllListener(onQueryAllListener);
        queryAll(MusicBefore.class,null);
    }



}
