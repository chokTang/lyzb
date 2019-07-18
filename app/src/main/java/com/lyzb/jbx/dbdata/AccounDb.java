package com.lyzb.jbx.dbdata;

import com.lyzb.jbx.dbdata.manger.DataBaseManager;
import com.lyzb.jbx.model.account.AccountModel;

/**
 * Created by longshao on 2017/8/2.
 */

public class AccounDb {

    private AccounDb() {
    }

    private static final class Holder {
        private static final AccounDb INSTANCE = new AccounDb();
    }

    public static AccounDb getIntance() {
        return Holder.INSTANCE;
    }

    public void insert(AccountModel model) {
        DataBaseManager.getIntance().getAccountDao().insertOrReplace(model);
    }
}
