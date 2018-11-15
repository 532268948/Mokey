package com.example.lib_common.base.db.dao;

import android.content.Context;

import com.example.lib_common.base.db.DaoUtil;
import com.example.lib_common.base.db.entity.User;
import com.example.lib_common.base.db.entity.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * author: tianhuaye
 * date:   2018/11/15 15:54
 * description:
 */
public class UserDaoUtil extends DaoUtil<User> {
    public UserDaoUtil(Context context) {
        super(context);
    }

    /**
     * 单个插入
     *
     * @param user
     */
    public void insertUser(User user) {
        insertSingle(user);
    }

    /**
     * 批量插入
     */
    public void insertBatchUser(List<User> list) {
        insertBatch(User.class, list);
    }

    /**
     * 条件查询
     */
    public void queryWhereUser(long memberId) {
        query(User.class, UserDao.Properties.Id.eq(memberId));
    }

    /**
     * 查询全部
     */
    public void queryAllUser() {
        queryAll(User.class, null);
//        return users;
    }

    /**
     * 条件删除
     */
    public void deleteSingleUser(long memberId) {
        User user = new User();
        user.setId(memberId);
        deleteSingle(user);
    }

    /**
     * 批量删除
     */
    public void deleteBatchUser(List<Long> list) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            User user = new User();
            user.setId(list.get(i));
            userList.add(user);
        }
        deleteBatch(User.class, userList);
    }

    /**
     * 单个更新
     */
    public void updateSingleUser(User user) {
        updateSingle(User.class, user);
    }

    /**
     * 批量更新
     */
    public void updateBatchUser(List<User> list) {
        updateBatch(User.class, list);
    }

    /**
     * 根据Id 单个删除
     */
    public void deleteByIdSingleUser(long memberId) {
        deleteByIdSingle(User.class, memberId);
    }

    /**
     * 根据Id批量删除
     */
    public void deleteByIdBatchUser(List<Long> longList) {
        deleteByIdBatch(User.class, longList);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        deleteAll(User.class);
    }
//    /**
//     * 条件批量删除
//     */
//    public void deleteWhereBatch(String ageString){
//        Query<User> build = daoSession.queryBuilder(User.class).where(UserDao.Properties.Age.eq(ageString)).build();
//        AsyncSession asyncSession = daoSession.startAsyncSession();
//        asyncSession.setListenerMainThread(new AsyncOperationListener() {
//            @Override
//            public void onAsyncOperationCompleted(AsyncOperation operation) {
//                if(operation.isCompletedSucessfully()){
//                    deleteBatch(User.class, (List<User> )operation.getResult());
//                }
//            }
//        });
//        asyncSession.queryList(build);
//    }
}
