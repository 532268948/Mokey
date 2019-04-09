package com.example.lib_common.db;

import java.util.List;

/**
 * author: tianhuaye
 * date:   2018/11/15 14:54
 * description:
 */
public interface DbOperateListener {

    interface OnInsertListener<T>{//单个插入成功
        void onInsertListener(boolean type);
    }

    interface OnQuerySingleListener<T>{//单个查询成功
        void onQuerySingleListener(T entry);
    }

    interface OnDeleteListener<T>{//单个删除成功
        void onDeleteListener(boolean type);
    }

    interface OnUpdateListener<T>{//单个修改成功
        void onUpdateListener(boolean type);
    }

    interface OnQueryAllListener<T>{//批量查询数据回调
        void onQueryAllBatchListener(List<T> list);
    }
}
