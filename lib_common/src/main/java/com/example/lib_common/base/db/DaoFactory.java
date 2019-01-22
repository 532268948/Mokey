package com.example.lib_common.base.db;

import com.example.lib_common.base.db.dao.UserDaoUtil;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/13
 * @time : 21:20
 * @email : 15869107730@163.com
 * @note :
 */
public class DaoFactory {
    private static DaoFactory mInstance = null;

    /**
     * 获取DaoFactory的实例
     *
     * @return
     */
    public static DaoFactory getInstance() {
        if (mInstance == null) {
            synchronized (DaoFactory.class) {
                if (mInstance == null) {
                    mInstance = new DaoFactory();
                }
            }
        }
        return mInstance;
    }

    /**
     * 得到用户信息数据表
     *
     * @return
     */
    public DaoUtil getUserDB() {
        return new UserDaoUtil();
    }

}
