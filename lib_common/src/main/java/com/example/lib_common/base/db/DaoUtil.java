package com.example.lib_common.base.db;

import com.example.lib_common.base.db.entity.DaoSession;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * author: tianhuaye
 * date:   2018/11/15 15:39
 * description:
 */
public class DaoUtil<T> extends DbOperate {
    private DBManager daoManager;
    public DaoSession daoSession;

    public DaoUtil() {
        daoManager = DBManager.getInstance();
        daoSession = daoManager.getDaoSession();
    }

    /**
     * 条件查询数据
     *
     * @param cls
     * @return
     */
    public <T> void query(Class cls, WhereCondition whereCondition) {
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && onQuerySingleListener != null) {
                    onQuerySingleListener.onQuerySingleListener(operation.getResult());
                } else if (onQuerySingleListener != null) {
                    onQuerySingleListener.onQuerySingleListener(null);
                }
            }
        });
        Query query = daoSession.queryBuilder(cls).where(whereCondition).build();
        asyncSession.queryUnique(query);
    }

    public T query(Class cls, String whereString, String[] params) {
        return (T) daoSession.getDao(cls).queryRaw(whereString, params);
    }

    /**
     * 批量查询
     *
     * @param object
     * @return
     */
    public void queryAll(Class object, Query<T> query) {
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                List<T> result = (List<T>) operation.getResult();
                onQueryAllListener.onQueryAllBatchListener(result);
            }
        });
        if (query == null) {
            asyncSession.loadAll(object);
        } else {
            asyncSession.queryList(query);
        }
    }

    /**
     * 删除
     */
    public void deleteSingle(T entry) {
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && onDeleteListener != null) {
                    onDeleteListener.onDeleteListener(true);
                } else if (onDeleteListener != null) {
                    onDeleteListener.onDeleteListener(false);
                }
            }
        });
        asyncSession.delete(entry);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(Class cls, final List<T> list) {
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && onDeleteListener != null) {
                    onDeleteListener.onDeleteListener(true);
                } else if (onDeleteListener != null) {
                    onDeleteListener.onDeleteListener(false);
                }
            }
        });
        asyncSession.deleteInTx(cls, list);
    }

    /**
     * 根据Id单个删除
     */
    public void deleteByIdSingle(Class cls, long longParams) {//此处longParams数值类型必须为主键id的类型
        daoSession.getDao(cls).deleteByKey(longParams);
    }

    /**
     * 根据Id批量删除
     */
    public void deleteByIdBatch(Class cls, List<Long> longList) {//同上
        daoSession.getDao(cls).deleteByKeyInTx(longList);
    }

    /**
     * 删除所有数据
     */
    public void deleteAll(Class cls) {
        final AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && onDeleteListener != null) {
                    onDeleteListener.onDeleteListener(true);
                } else if (onDeleteListener != null) {
                    onDeleteListener.onDeleteListener(false);
                }
            }
        });
        asyncSession.deleteAll(cls);
    }

    /**
     * 插入一条数据
     */
    public void insertSingle(final T entity) {
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && onInsertListener != null) {
                    onInsertListener.onInsertListener(true);
                } else if (onInsertListener != null) {
                    onInsertListener.onInsertListener(false);
                }
            }
        });
        asyncSession.runInTx(new Runnable() {
            @Override
            public void run() {
                daoSession.insert(entity);
            }
        });
    }

    /**
     * 批量插入
     */
    public <T> void insertBatch(final Class cls, final List<T> userList) {
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && onInsertListener != null) {
                    onInsertListener.onInsertListener(true);
                } else if (onInsertListener != null) {
                    onInsertListener.onInsertListener(false);
                }
            }
        });
        asyncSession.runInTx(new Runnable() {
            @Override
            public void run() {
                for (T object : userList) {
                    daoSession.insertOrReplace(object);
                }
            }
        });
    }

    /**
     * 更新一个数据
     */
    public <T> void updateSingle(Class cls, T entry) {
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && onUpdateListener != null) {
                    onUpdateListener.onUpdateListener(true);
                } else if (onUpdateListener != null) {
                    onUpdateListener.onUpdateListener(false);
                }
            }
        });
        asyncSession.update(entry);
    }

    /**
     * 批量更新数据
     */
    public <T> void updateBatch(final Class cls, final List<T> tList) {
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && onUpdateListener != null) {
                    onUpdateListener.onUpdateListener(true);
                } else if (onUpdateListener != null) {
                    onUpdateListener.onUpdateListener(false);
                }
            }
        });
        asyncSession.updateInTx(cls, tList);
    }

}
