package com.example.lib_common.base.db;

import android.content.Context;

import com.example.lib_common.base.db.entity.DaoMaster;
import com.example.lib_common.base.db.entity.DaoSession;

/**
 * author: tianhuaye
 * date:   2018/11/15 15:20
 * description:
 */
public class DBManager {
    private static DBManager daoManager;
    private DaoMaster daoMaster;
    private DaoSession daoSession = null;
    private DaoMaster.DevOpenHelper devOpenHelper;

    private DBManager(){};
    public static DBManager getInstance(){
        if(daoManager == null){
            daoManager = new DBManager();
        }
        return daoManager;
    }
    public DaoMaster getDaoManager(Context context, String DB_NAME){
        if(daoMaster == null){
            devOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        }
        return daoMaster;
    }
    public DaoSession getDaoSession(){
        if(daoSession == null){
            daoSession = daoMaster.newSession();
        }
        return  daoSession;
    }

    /**
     * 关闭DaoSession
     */
    private void closeDaoSession(){
        if(daoSession != null){
            daoSession.clear();
            daoSession = null;
        }
    }
    /**
     * 关闭Helper
     */
    private void closeHelper(){
        if(devOpenHelper != null){
            devOpenHelper.close();
            devOpenHelper = null;
        }
    }
    /**
     * 关闭所有的操作
     */
    public void closeConnection(){
        closeDaoSession();
        closeHelper();
    }

}
