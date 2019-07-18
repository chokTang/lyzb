package com.lyzb.jbx.dbdata.manger;

import android.content.Context;

import com.lyzb.jbx.model.account.AccountModelDao;
import com.lyzb.jbx.model.account.DaoMaster;
import com.lyzb.jbx.model.account.DaoSession;
import com.lyzb.jbx.model.search.HistroyModelDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by longshao on 2017/8/1.
 */

public class DataBaseManager {

    private DaoSession mDaoSession = null;
    private AccountModelDao modelDao = null;
    private HistroyModelDao histroyModelDao = null;

    private DataBaseManager() {
    }

    private static final class Holder {
        private static final DataBaseManager INSTANCE = new DataBaseManager();
    }

    private void initDao(Context context) {
        final DataOpenHelper helper = new DataOpenHelper(context, "long_ec.db");
        final Database database = helper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();
        modelDao = mDaoSession.getAccountModelDao();
        histroyModelDao = mDaoSession.getHistroyModelDao();
    }

    public static DataBaseManager getIntance() {
        return Holder.INSTANCE;
    }

    public DataBaseManager init(Context context) {
        initDao(context);
        return this;
    }

    /**
     * 获取到某个数据库的中实体
     *
     * @return
     */
    public final AccountModelDao getAccountDao() {
        return modelDao;
    }

    public final HistroyModelDao getHistroyModelDao() {
        return histroyModelDao;
    }
}
